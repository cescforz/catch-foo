package cn.cescforz.foo.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-17 17:46
 */
@Aspect
@Component
public class WebAopAspect {

    private static final Logger LOG = LoggerFactory.getLogger(WebAopAspect.class);
    private static final ThreadLocal<Long> TIME_TREAD_LOCAL = new ThreadLocal<>();


    /**
     *  切点表达式：..两个点表明多个，*代表一个。下面表达式代表切入com.cescforz.controller包下的所有类的所有方法，方法参数不限，返回类型不限。
     *  其中访问修饰符可以不写，不能用*，，第一个*代表返回类型不限，第二个*表示所有类，第三个*表示所有方法，..两个点表示方法里的参数不限。
     *  然后用@Pointcut切点注解，想在一个空方法上面，一会儿在Advice通知中，直接调用这个空方法就行了。
     *  也可以把切点表达式写在Advice通知中的，单独定义出来主要是为了好管理。
     */
    private final String POINT_CUT = "execution(public * cn.cescforz.bar.bean.*.*(..))";
    @Pointcut(POINT_CUT)
    public void pointCut(){}

    /**
     * controller请求开始时调用
     * @param joinPoint
     */
    @Before(POINT_CUT)
    public void before(JoinPoint joinPoint){
        TIME_TREAD_LOCAL.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求的request
        HttpServletRequest request = attributes.getRequest();
        //获取所有请求的参数，封装为map对象
        // Map<String,Object> parameterMap = getParameterMap(request);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取被拦截的方法
        Method method = methodSignature.getMethod();
        //获取被拦截的方法名
        String methodName = method.getName();
        LOG.info("AOP begin ,请求开始方法  :{}", method.getDeclaringClass() + "." + methodName + "()");
        //获取所有请求参数key和value
        String keyValue = getReqParameter(request);
        LOG.info("请求url = {}", request.getRequestURL().toString());
        LOG.info("请求方法requestMethod = {}", request.getMethod());
        LOG.info("请求资源uri = {}", request.getRequestURI());
        LOG.info("所有的请求参数 key：value = {}", keyValue);
    }


    /**
     * controller请求结束返回时调用
     *      如果第一个参数为JoinPoint，则第二个参数为返回值的信息
     *      如果第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     *      returning：限定了只有目标方法返回值与通知方法参数类型匹配时才能执行后置返回通知，否则不执行，
     *      参数为Object类型将匹配任何目标返回值
     */
    @AfterReturning(returning = "result", pointcut = POINT_CUT)
    public Object afterReturn(JoinPoint joinPoint, Object result) {
        LOG.info("AOP afterReturn,返回值result = {}", result.toString());
        long startTime = TIME_TREAD_LOCAL.get();
        double callTime = (System.currentTimeMillis() - startTime) / 1000.0;
        LOG.info("调用controller花费时间time = {}s", callTime);
        //当接口请求时间大于3秒时，标记为异常调用时间，并记录入库

        TIME_TREAD_LOCAL.remove();
        return result;
    }


    @After(value = "pointCut()")
    public void doAfterAdvice(JoinPoint joinPoint){
        LOG.info("后置通知执行了!");
    }

    /**
     * 后置异常通知
     *  定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
     *  throwing:限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
     *            对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(value = POINT_CUT,throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception){


        LOG.error("发现异常！",exception.getMessage());
        LOG.error(JSON.toJSONString(exception.getStackTrace()));
        writeContent("出现异常");
    }

    /**
     * 将内容输出到浏览器
     * @param content
     */
    private void writeContent(String content) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain;charset=UTF-8");
        response.setHeader("icop-content-type", "exception");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            LOG.error("context:",e);
        }
        writer.print(content);
        writer.flush();
        writer.close();
    }





    /**
     * 获取所有请求参数，封装为map对象
     * @param request
     * @return
     */
    public Map<String, Object> getParameterMap(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        StringBuilder stringBuilder = new StringBuilder();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getParameter(key);
            String keyValue = key + " : " + value + " ; ";
            stringBuilder.append(keyValue);
            parameterMap.put(key, value);
        }
        return parameterMap;
    }



    private String getReqParameter(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Enumeration<String> enumeration = request.getParameterNames();
        JSONArray jsonArray = new JSONArray();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getParameter(key);
            JSONObject json = new JSONObject();
            json.put(key, value);
            jsonArray.add(json);
        }
        return jsonArray.toString();
    }


    /**
     *  "execution(public * com.xhx.springboot.controller.*.*(..))"
     *  *只能匹配一级路径
     *  ..可以匹配多级，可以是包路径，也可以匹配多个参数
     *  + 只能放在类后面，表明本类及所有子类
     *
     *  @Pointcut("execution(* *..get*(Long,..))")   ==>所有get开头的，第一个参数是Long类型的
     *
     *  下面用来表示com.xhx.springboot包及其子包下的所有类方法
     *  "within(com.xhx.springboot..*)"   ==>within(类路径)   用来限定类，同样可以使用匹配符
     *
     *  执行顺序
     *  @Around环绕通知   属于环绕增强，能控制切点执行前，执行后，，用这个注解后，程序抛异常，会影响@AfterThrowing这个注解
     *  @Before通知执行
     *  @Before通知执行结束
     *  @Around环绕通知执行结束
     *  @After后置通知执行了!
     *  @AfterReturning第一个后置返回通知的返回值：18
     *
     *
     *  凡是注解了RequestMapping的方法都被拦截
     *  @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
     *  private void webPointcut() {}
     *
     */
}
