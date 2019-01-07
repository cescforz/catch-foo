package cn.cescforz.foo.annotation.security.encrypt;

import cn.cescforz.foo.enumeration.EncryptBodyMethod;
import cn.cescforz.foo.enumeration.SHAEncryptType;

import java.lang.annotation.*;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 加密{@link org.springframework.web.bind.annotation.ResponseBody}响应数据，可用于整个控制类或者某个控制器上</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-24 09:48
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EncryptBody {

    EncryptBodyMethod value() default EncryptBodyMethod.MD5;
    String otherKey() default "";
    SHAEncryptType shaType() default SHAEncryptType.SHA256;
}
