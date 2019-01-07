package cn.cescforz.foo.advice;

import cn.cescforz.foo.annotation.security.encrypt.*;
import cn.cescforz.foo.config.EncryptBodyConfig;
import cn.cescforz.foo.enumeration.EncryptBodyMethod;
import cn.cescforz.foo.enumeration.SHAEncryptType;
import cn.cescforz.foo.exception.encrypt.EncryptBodyFailException;
import cn.cescforz.foo.exception.encrypt.EncryptMethodNotFoundException;
import cn.cescforz.foo.bean.model.EncryptAnnotationInfoBean;
import cn.cescforz.foo.util.CheckUtils;
import cn.cescforz.foo.util.encrypt.AESEncryptUtils;
import cn.cescforz.foo.util.encrypt.DESEncryptUtils;
import cn.cescforz.foo.util.encrypt.MD5EncryptUtils;
import cn.cescforz.foo.util.encrypt.SHAEncryptUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 响应数据的加密处理</p>
 *   本类只对控制器参数中含有<strong>{@link org.springframework.web.bind.annotation.ResponseBody}</strong>
 *   或者控制类上含有<strong>{@link org.springframework.web.bind.annotation.RestController}</strong>
 *   以及package为<strong><code>cn.cescforz.bar.annotation.security.encrypt</code></strong>下的注解有效
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-24 23:50
 */
@Order(1)
@ControllerAdvice
@Slf4j
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice {


    private final ObjectMapper objectMapper;
    private final EncryptBodyConfig config;

    @Autowired
    public EncryptResponseBodyAdvice(ObjectMapper objectMapper, EncryptBodyConfig config) {
        this.objectMapper = objectMapper;
        this.config = config;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        Annotation[] annotations = returnType.getDeclaringClass().getAnnotations();
        if(annotations!=null && annotations.length>0){
            for (Annotation annotation : annotations) {
                if(annotation instanceof EncryptBody ||
                        annotation instanceof AESEncryptBody ||
                        annotation instanceof DESEncryptBody ||
                        annotation instanceof RSAEncryptBody ||
                        annotation instanceof MD5EncryptBody ||
                        annotation instanceof SHAEncryptBody){
                    return true;
                }
            }
        }
        return returnType.getMethod().isAnnotationPresent(EncryptBody.class) ||
                returnType.getMethod().isAnnotationPresent(AESEncryptBody.class) ||
                returnType.getMethod().isAnnotationPresent(DESEncryptBody.class) ||
                returnType.getMethod().isAnnotationPresent(RSAEncryptBody.class) ||
                returnType.getMethod().isAnnotationPresent(MD5EncryptBody.class) ||
                returnType.getMethod().isAnnotationPresent(SHAEncryptBody.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body==null) {
            return null;
        }
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        String str = null;
        try {
            str = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            log.error("context:",e);
        }
        EncryptAnnotationInfoBean classAnnotation = getClassAnnotation(returnType.getDeclaringClass());
        if(classAnnotation!=null){
            return switchEncrypt(str, classAnnotation);
        }
        EncryptAnnotationInfoBean methodAnnotation = getMethodAnnotation(returnType);
        if(methodAnnotation!=null){
            return switchEncrypt(str, methodAnnotation);
        }
        throw new EncryptBodyFailException();
    }



    /**
     * <p>Description: 获取方法控制器上的加密注解信息</p>
     * @param methodParameter 控制器方法
     * @return cn.cescforz.bar.bean.model.EncryptAnnotationInfoBean 加密注解信息
     */
    private EncryptAnnotationInfoBean getMethodAnnotation(MethodParameter methodParameter){
        if(methodParameter.getMethod().isAnnotationPresent(EncryptBody.class)){
            EncryptBody encryptBody = methodParameter.getMethodAnnotation(EncryptBody.class);
            return EncryptAnnotationInfoBean.builder()
                    .encryptBodyMethod(encryptBody.value())
                    .key(encryptBody.otherKey())
                    .shaEncryptType(encryptBody.shaType())
                    .build();
        }
        if(methodParameter.getMethod().isAnnotationPresent(MD5EncryptBody.class)){
            return EncryptAnnotationInfoBean.builder()
                    .encryptBodyMethod(EncryptBodyMethod.MD5)
                    .build();
        }
        if(methodParameter.getMethod().isAnnotationPresent(SHAEncryptBody.class)){
            return EncryptAnnotationInfoBean.builder()
                    .encryptBodyMethod(EncryptBodyMethod.SHA)
                    .shaEncryptType(methodParameter.getMethodAnnotation(SHAEncryptBody.class).value())
                    .build();
        }
        if(methodParameter.getMethod().isAnnotationPresent(DESEncryptBody.class)){
            return EncryptAnnotationInfoBean.builder()
                    .encryptBodyMethod(EncryptBodyMethod.DES)
                    .key(methodParameter.getMethodAnnotation(DESEncryptBody.class).otherKey())
                    .build();
        }
        if(methodParameter.getMethod().isAnnotationPresent(AESEncryptBody.class)){
            return EncryptAnnotationInfoBean.builder()
                    .encryptBodyMethod(EncryptBodyMethod.AES)
                    .key(methodParameter.getMethodAnnotation(AESEncryptBody.class).otherKey())
                    .build();
        }
        return null;
    }

    /**
     * <p>Description: 获取类控制器上的加密注解信息</p>
     * @param clazz clazz 控制器类
     * @return cn.cescforz.bar.bean.model.EncryptAnnotationInfoBean
     */
    private EncryptAnnotationInfoBean getClassAnnotation(Class clazz){
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        if(annotations!=null && annotations.length>0){
            for (Annotation annotation : annotations) {
                if(annotation instanceof EncryptBody){
                    EncryptBody encryptBody = (EncryptBody) annotation;
                    return EncryptAnnotationInfoBean.builder()
                            .encryptBodyMethod(encryptBody.value())
                            .key(encryptBody.otherKey())
                            .shaEncryptType(encryptBody.shaType())
                            .build();
                }
                if(annotation instanceof MD5EncryptBody){
                    return EncryptAnnotationInfoBean.builder()
                            .encryptBodyMethod(EncryptBodyMethod.MD5)
                            .build();
                }
                if(annotation instanceof SHAEncryptBody){
                    return EncryptAnnotationInfoBean.builder()
                            .encryptBodyMethod(EncryptBodyMethod.SHA)
                            .shaEncryptType(((SHAEncryptBody) annotation).value())
                            .build();
                }
                if(annotation instanceof DESEncryptBody){
                    return EncryptAnnotationInfoBean.builder()
                            .encryptBodyMethod(EncryptBodyMethod.DES)
                            .key(((DESEncryptBody) annotation).otherKey())
                            .build();
                }
                if(annotation instanceof AESEncryptBody){
                    return EncryptAnnotationInfoBean.builder()
                            .encryptBodyMethod(EncryptBodyMethod.AES)
                            .key(((AESEncryptBody) annotation).otherKey())
                            .build();
                }
            }
        }
        return null;
    }

    /**
     * <p>Description: 选择加密方式并进行加密</p>
     * @param formatStringBody 目标加密字符串
     * @param infoBean 加密信息
     * @return java.lang.String 加密结果
     */
    private String switchEncrypt(String formatStringBody,EncryptAnnotationInfoBean infoBean){
        EncryptBodyMethod method = infoBean.getEncryptBodyMethod();
        if(method==null){
            throw new EncryptMethodNotFoundException();
        }
        if(method == EncryptBodyMethod.MD5){
            return MD5EncryptUtils.encrypt(formatStringBody);
        }
        if(method == EncryptBodyMethod.SHA){
            SHAEncryptType shaEncryptType = infoBean.getShaEncryptType();
            if(shaEncryptType==null) {
                shaEncryptType = SHAEncryptType.SHA256;
            }
            return SHAEncryptUtils.encrypt(formatStringBody,shaEncryptType);
        }
        String key = infoBean.getKey();
        if(method == EncryptBodyMethod.DES){
            key = CheckUtils.checkAndGetKey(config.getAesKey(),key,"DES-KEY");
            return DESEncryptUtils.encrypt(formatStringBody,key);
        }
        if(method == EncryptBodyMethod.AES){
            key = CheckUtils.checkAndGetKey(config.getAesKey(),key,"AES-KEY");
            return AESEncryptUtils.encrypt(formatStringBody,key);
        }
        throw new EncryptBodyFailException();
    }
}
