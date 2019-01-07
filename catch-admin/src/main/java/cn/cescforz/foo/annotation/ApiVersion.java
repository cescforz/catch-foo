package cn.cescforz.foo.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 版本控制注解</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-17 21:19
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {

    int value();
}
