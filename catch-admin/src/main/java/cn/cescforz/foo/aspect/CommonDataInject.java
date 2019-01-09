package cn.cescforz.foo.aspect;

import cn.cescforz.foo.util.StringCustomUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: 公共数据注入</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-09 16:37
 */
@Slf4j
@Aspect
@Component
public class CommonDataInject {


    @Pointcut("execution(* cn.cescforz.foo.mapper.*Mapper.insert*(..))")
    private void insertCutMethod() {
    }

    @Pointcut("execution(* cn.cescforz.foo.mapper.*Mapper.update*(..))")
    private void updateCutMethod() {
    }



    @Around("insertCutMethod()")
    public Object doInsertAround(ProceedingJoinPoint pjp) throws Throwable {
        for (Object obj : pjp.getArgs()) {
            for(Method method : obj.getClass().getMethods()){
                if (StringCustomUtils.isEquals(method.getName(), "setCreateDate")) {
                    method.invoke(obj, new Date());
                }
            }
        }
        return pjp.proceed();
    }

    @Around("updateCutMethod()")
    public Object doUpdateAround(ProceedingJoinPoint pjp) throws Throwable {
        for (Object obj : pjp.getArgs()) {
            for (Method method : obj.getClass().getMethods()) {
                if (StringCustomUtils.isEquals(method.getName(), "setUpdateDate")) {
                    method.invoke(obj, new Date());
                }
            }
        }
        return pjp.proceed();
    }
}
