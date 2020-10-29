package com.xinou.lawfrim.web.config;

import cn.hutool.core.util.StrUtil;
import com.xinou.lawfrim.common.enumeration.TokenTypeEnum;
import com.xinou.lawfrim.common.util.BusException;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.web.base.JwtModel;
import com.xinou.lawfrim.web.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lizhongyuan
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //进入方法之前进行的操作
        //获取token
        String token = request.getHeader("token");
        //如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if(method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
        }
        if(method.isAnnotationPresent(SysLoginToken.class)) {
            SysLoginToken sysLoginToken = method.getAnnotation(SysLoginToken.class);
            if(sysLoginToken.required()){
                return checkToken(token, request, TokenTypeEnum.TYPE_SYS);
            }
        }
        if (method.isAnnotationPresent(WebLoginToken.class)){
            WebLoginToken webLoginToken = method.getAnnotation(WebLoginToken.class);
            if(webLoginToken.required()){
                return checkToken(token, request, TokenTypeEnum.TYPE_WEB);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //方法处理之后但是并未渲染视图的时候进行的操作
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //渲染视图之后进行的操作
    }

    private boolean checkToken(String token, HttpServletRequest request, TokenTypeEnum typeEnum){
        //校验token
        if (StrUtil.isEmpty(token)){
            throw new BusException(Config.RE_CODE_NO_HAVE_TOKEN, Config.RE_MSG_NO_HAVE_TOKEN);
        }
        JwtModel jwtModel = JwtUtil.checkToken(token);
        if (!typeEnum.getType().equals(jwtModel.getType())){
            throw new BusException(Config.RE_ACCOUNT_NOT_EXIST_CODE, Config.RE_ACCOUNT_NOT_EXIST_MSG);
        }
        request.setAttribute("userId",jwtModel.getUserId().toString());
//        Map<String, String> map = new HashMap<>(2);
//        map.put("userId", jwtModel.getUserId().toString());
//        modifyHeaders(map,request);
        return true;
    }

    /**
     * 修改请求头信息
     * @param headerses headerses
     * @param request request
     */
    private void modifyHeaders(Map<String, String> headerses, HttpServletRequest request) {
        if (headerses == null || headerses.isEmpty()) {
            return;
        }
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders)headers.get(o1);
            for (Map.Entry<String, String> entry : headerses.entrySet()) {
                o2.removeHeader(entry.getKey());
                o2.addValue(entry.getKey()).setString(entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
