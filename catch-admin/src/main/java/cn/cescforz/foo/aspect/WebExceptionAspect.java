package cn.cescforz.foo.aspect;

import cn.cescforz.foo.bean.domain.ErrorLog;
import cn.cescforz.foo.bean.dto.ResponseDTO;
import cn.cescforz.foo.config.mq.producer.Producer;
import cn.cescforz.foo.constant.RocketMQConstants;
import cn.cescforz.foo.enumeration.ResponseCode;
import cn.cescforz.foo.exception.ServerTransException;
import cn.cescforz.foo.util.IllegalStrFilterUtils;
import cn.cescforz.foo.util.StringCustomUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-17 20:11
 */
@Aspect
@Component
@Slf4j
public class WebExceptionAspect {

    @Value("${interface.request.timeout}")
    private int performanceBadValue;

    /**异常入库生产者*/
    private Producer<ErrorLog> producer;

    @Autowired
    public WebExceptionAspect(Producer<ErrorLog> producer) {
        this.producer = producer;
    }

    /**切点位置*/
    private static final String POINT_CUT = "execution(* cn.cescforz.foo.controller.*.*(..))";

    @Pointcut(POINT_CUT)
    public void pointCut(){}


    @Around("pointCut()")
    public ResponseDTO handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable{
        Stopwatch stopwatch = Stopwatch.createStarted();
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        try {
            String methodName = String.valueOf(pjp.getSignature());
            String param = StringCustomUtils.arraysToStr(pjp.getArgs());
            log.info("执行Controller开始:{};参数:{}", methodName,param);
            //处理入参特殊字符和sql注入攻击
            checkRequestParam(pjp);
            //执行访问接口操作-->获取返回参数
            Object proceed = pjp.proceed();
            responseDTO.setData(proceed);
            log.info("执行Controller结束:{}， 返回值:{}",methodName, JSON.toJSONString(responseDTO,true));
            Long consumeTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
            log.info("耗时:{}(毫秒).",consumeTime);
            //当接口请求时间大于3秒时，标记为异常调用时间，并记录入库
            if(consumeTime > performanceBadValue){
                recordException(methodName,param,null,consumeTime);
            }
        } catch (Exception throwable) {
            responseDTO = handlerException(pjp, throwable);
        }
        return responseDTO;
    }
    
    /**
     * <p>Description: 处理异常并包装返回参数</p>
     * @param pjp
     * @param e
     * @return cn.cescforz.bar.bean.dto.ResponseDTO<java.lang.Object>
     */
    private ResponseDTO<Object> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        String methodName = String.valueOf(pjp.getSignature());
        String param = StringCustomUtils.arraysToStr(pjp.getArgs());
        ResponseDTO<Object> responseDTO;
        if(e.getClass().isAssignableFrom(ServerTransException.class) ){
            ServerTransException stException = (ServerTransException)e;
            log.error("捕获到ServerTransException异常:{}",JSONObject.toJSONString(stException.getResponseCode()));
            responseDTO = new ResponseDTO<>(false,stException.getErrCode(),stException.getMessage());
        } else if (e instanceof RuntimeException) {
            log.error(String.format("RuntimeException{方法:%s,参数:%s,异常:%s}",methodName,param,e.getMessage()),e);
            responseDTO = new ResponseDTO<>(false,null,e.getMessage());
        } else {
            log.error(String.format("Exception{方法:%s,参数:%s,异常:%s}",methodName,param,e.getMessage()),e);
            responseDTO = new ResponseDTO<>(false,null,e.getMessage());
        }
        recordException(methodName,param,e,null);
        return responseDTO;
    }

    /**
     * <p>Description: 异常或接口调用时间过长信息入库</p>
     * @param methodName 调用方法
     * @param param 请求参数
     * @param e 异常
     * @param consumeTime 调用时间
     */
    private void recordException(String methodName,String param,Throwable e,Long consumeTime){
        ErrorLog errorLog = new ErrorLog();
        errorLog.setInterfaceName(methodName);
        errorLog.setRequestParam(param);
        if (null != e) {
            StackTraceElement stackTraceElement= e.getStackTrace()[0];
            String errorInfo = String.format("%s,errorMassage:%s,errorLine:%d",e.toString(),stackTraceElement,stackTraceElement.getLineNumber());
            errorLog.setLogInfo(errorInfo);
        }
        if (null != consumeTime) {
            errorLog.setConsumeTime(consumeTime);
        }
        errorLog.setModuleType(1);
        errorLog.setCreateDate(new Date());
        producer.sendTxMsg(errorLog, RocketMQConstants.CATCH_FOO_TOPIC, RocketMQConstants.HANDLE_EXCEPTIONS_TAG, RocketMQConstants.CATCH_BAR_KEY);
    }


    private void checkRequestParam(ProceedingJoinPoint pjp){
        String methodName = String.valueOf(pjp.getSignature());
        String param = StringCustomUtils.arraysToStr(pjp.getArgs());
        if (!IllegalStrFilterUtils.sqlStrFilter(param)) {
            String warning = String.format("访问接口:%s,输入参数存在SQL注入风险！参数为:%s",methodName,param);
            log.warn(warning);
            throw new ServerTransException(ResponseCode.ILLEGAL_ARGUMENT_EXCEPTION,warning);
        }
        if (IllegalStrFilterUtils.isIllegalStr(param)) {
            String warning = String.format("访问接口:%s,输入参数含有非法字符! 参数为:%s",methodName,param);
            log.warn(warning);
            throw new ServerTransException(ResponseCode.ILLEGAL_ARGUMENT_EXCEPTION,warning);
        }
    }
}
