package cn.cescforz.foo.Interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 提供 API 的时候，经常需要对 API 进行统一的拦截，比如进行接口的安全性校验</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-17 16:25
 */
public class ApiInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(ApiInterceptor.class);
    /**
     * 请求之前
     * @param request
     * @param response
     * @param handler
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.info("进入拦截器");
        return true;
    }

    /**
     * 请求时
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求完成
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
