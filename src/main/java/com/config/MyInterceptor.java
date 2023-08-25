package com.config;
import com.alibaba.druid.util.StringUtils;
import com.pojo.Rep;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class MyInterceptor implements HandlerInterceptor {
    private final StringRedisTemplate redisTemplate;
    public MyInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求处理之前调用，返回true继续执行后续拦截器和请求处理，返回false则不会继续执行
        String headerToken = request.getHeader("token");
        if(!StringUtils.isEmpty(headerToken)){
            String userName = headerToken.split("-")[0];
            String token = redisTemplate.opsForValue().get(Rep.TOKEN + userName);
            if(!headerToken.equals(token)){
                response.setStatus(507);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理之后调用，但在视图被渲染之前，可对ModelAndView进行操作
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在整个请求结束之后调用，可用于进行一些资源清理操作
    }
}