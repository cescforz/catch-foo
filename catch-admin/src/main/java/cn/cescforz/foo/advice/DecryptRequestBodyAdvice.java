package cn.cescforz.foo.advice;

import cn.cescforz.foo.annotation.security.decrypt.AESDecryptBody;
import cn.cescforz.foo.annotation.security.decrypt.DESDecryptBody;
import cn.cescforz.foo.annotation.security.decrypt.DecryptBody;
import cn.cescforz.foo.annotation.security.decrypt.RSADecryptBody;
import cn.cescforz.foo.config.EncryptBodyConfig;
import cn.cescforz.foo.enumeration.DecryptBodyMethod;
import cn.cescforz.foo.exception.encrypt.DecryptBodyFailException;
import cn.cescforz.foo.exception.encrypt.DecryptMethodNotFoundException;
import cn.cescforz.foo.bean.model.DecryptAnnotationInfoBean;
import cn.cescforz.foo.bean.model.DecryptHttpInputMessage;
import cn.cescforz.foo.util.CheckUtils;
import cn.cescforz.foo.util.StringCustomUtils;
import cn.cescforz.foo.util.encrypt.AESEncryptUtils;
import cn.cescforz.foo.util.encrypt.DESEncryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 请求数据的加密信息解密处理<br>
 *  本类只对控制器参数中含有<strong>{@link org.springframework.web.bind.annotation.RequestBody}</strong>
 *  以及package为<strong><code>cn.cescforz.bar.annotation.security.decrypt</code></strong>下的注解有效</p>
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-23 23:31
 */
@Order(1)
@ControllerAdvice
@Slf4j
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {

    private EncryptBodyConfig config;

    @Autowired
    public DecryptRequestBodyAdvice(EncryptBodyConfig encryptBodyConfig) {
        this.config = encryptBodyConfig;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        Annotation[] annotations = methodParameter.getDeclaringClass().getAnnotations();
        if(annotations!=null && annotations.length>0){
            for (Annotation annotation : annotations) {
                if(annotation instanceof DecryptBody ||
                        annotation instanceof AESDecryptBody ||
                        annotation instanceof DESDecryptBody ||
                        annotation instanceof RSADecryptBody){
                    return true;
                }
            }
        }
        return methodParameter.getMethod().isAnnotationPresent(DecryptBody.class) ||
                methodParameter.getMethod().isAnnotationPresent(AESDecryptBody.class) ||
                methodParameter.getMethod().isAnnotationPresent(DESDecryptBody.class) ||
                methodParameter.getMethod().isAnnotationPresent(RSADecryptBody.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        if(inputMessage.getBody()==null){
            return inputMessage;
        }
        String body;
        try {
            body = IOUtils.toString(inputMessage.getBody(),config.getEncoding());
        }catch (Exception e){
            throw new DecryptBodyFailException("Unable to get request body data," +
                    " please check if the sending data body or request method is in compliance with the specification." +
                    " (无法获取请求正文数据，请检查发送数据体或请求方法是否符合规范。)");
        }
        if(body==null || StringCustomUtils.isBlank(body)){
            throw new DecryptBodyFailException("The request body is NULL or an empty string, so the decryption failed." +
                    " (请求正文为NULL或为空字符串，因此解密失败。)");
        }
        String decryptBody = null;
        DecryptAnnotationInfoBean methodAnnotation = this.getMethodAnnotation(parameter);
        if(methodAnnotation!=null){
            decryptBody = switchDecrypt(body,methodAnnotation);
        }else{
            DecryptAnnotationInfoBean classAnnotation = this.getClassAnnotation(parameter.getDeclaringClass());
            if(classAnnotation!=null){
                decryptBody = switchDecrypt(body,classAnnotation);
            }
        }
        if(decryptBody==null){
            throw new DecryptBodyFailException("Decryption error, " +
                    "please check if the selected source data is encrypted correctly." +
                    " (解密错误，请检查选择的源数据的加密方式是否正确。)");
        }
        try {
            InputStream inputStream = IOUtils.toInputStream(decryptBody, config.getEncoding());
            return new DecryptHttpInputMessage(inputStream,inputMessage.getHeaders());
        }catch (Exception e){
            throw new DecryptBodyFailException("The string is converted to a stream format exception." +
                    " Please check if the format such as encoding is correct." +
                    " (字符串转换成流格式异常，请检查编码等格式是否正确。)");
        }
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }



    /**
     * <p>Description: 获取类控制器上的加密注解信息</p>
     * @param methodParameter 控制器方法
     * @return cn.cescforz.bar.bean.model.DecryptAnnotationInfoBean
     */
    private DecryptAnnotationInfoBean getMethodAnnotation(MethodParameter methodParameter){
        if(methodParameter.getMethod().isAnnotationPresent(DecryptBody.class)){
            DecryptBody decryptBody = methodParameter.getMethodAnnotation(DecryptBody.class);
            return DecryptAnnotationInfoBean.builder()
                    .decryptBodyMethod(decryptBody.value())
                    .key(decryptBody.otherKey())
                    .build();
        }
        if(methodParameter.getMethod().isAnnotationPresent(DESDecryptBody.class)){
            return DecryptAnnotationInfoBean.builder()
                    .decryptBodyMethod(DecryptBodyMethod.DES)
                    .key(methodParameter.getMethodAnnotation(DESDecryptBody.class).otherKey())
                    .build();
        }
        if(methodParameter.getMethod().isAnnotationPresent(AESDecryptBody.class)){
            return DecryptAnnotationInfoBean.builder()
                    .decryptBodyMethod(DecryptBodyMethod.AES)
                    .key(methodParameter.getMethodAnnotation(AESDecryptBody.class).otherKey())
                    .build();
        }
        return null;
    }

    /**
     * <p>Description: 获取类控制器上的加密注解信息</p>
     * @param clazz 控制器类
     * @return cn.cescforz.bar.bean.model.DecryptAnnotationInfoBean
     */
    private DecryptAnnotationInfoBean getClassAnnotation(Class clazz){
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        if(annotations!=null && annotations.length>0){
            for (Annotation annotation : annotations) {
                if(annotation instanceof DecryptBody){
                    DecryptBody decryptBody = (DecryptBody) annotation;
                    return DecryptAnnotationInfoBean.builder()
                            .decryptBodyMethod(decryptBody.value())
                            .key(decryptBody.otherKey())
                            .build();
                }
                if(annotation instanceof DESDecryptBody){
                    return DecryptAnnotationInfoBean.builder()
                            .decryptBodyMethod(DecryptBodyMethod.DES)
                            .key(((DESDecryptBody) annotation).otherKey())
                            .build();
                }
                if(annotation instanceof AESDecryptBody){
                    return DecryptAnnotationInfoBean.builder()
                            .decryptBodyMethod(DecryptBodyMethod.AES)
                            .key(((AESDecryptBody) annotation).otherKey())
                            .build();
                }
            }
        }
        return null;
    }


    /**
     * <p>Description: 选择加密方式并进行解密</p>
     * @param formatStringBody 目标解密字符串
     * @param infoBean 加密信息
     * @return java.lang.String
     */
    private String switchDecrypt(String formatStringBody,DecryptAnnotationInfoBean infoBean){
        DecryptBodyMethod method = infoBean.getDecryptBodyMethod();
        if(method==null) {
            throw new DecryptMethodNotFoundException();
        }
        String key = infoBean.getKey();
        if(method == DecryptBodyMethod.DES){
            key = CheckUtils.checkAndGetKey(config.getAesKey(),key,"DES-KEY");
            return DESEncryptUtils.decrypt(formatStringBody,key);
        }
        if(method == DecryptBodyMethod.AES){
            key = CheckUtils.checkAndGetKey(config.getAesKey(),key,"AES-KEY");
            return AESEncryptUtils.decrypt(formatStringBody,key);
        }
        throw new DecryptBodyFailException();
    }

}
