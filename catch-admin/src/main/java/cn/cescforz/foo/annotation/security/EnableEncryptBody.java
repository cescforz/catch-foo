package cn.cescforz.foo.annotation.security;

import cn.cescforz.foo.advice.DecryptRequestBodyAdvice;
import cn.cescforz.foo.advice.EncryptResponseBodyAdvice;
import cn.cescforz.foo.config.EncryptBodyConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 启动类</p>
 *  使用方法：在SpringBoot的Application启动类上添加此注解即可
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-24 23:28
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EncryptBodyConfig.class,
        EncryptResponseBodyAdvice.class,
        DecryptRequestBodyAdvice.class})
public @interface EnableEncryptBody {
}
