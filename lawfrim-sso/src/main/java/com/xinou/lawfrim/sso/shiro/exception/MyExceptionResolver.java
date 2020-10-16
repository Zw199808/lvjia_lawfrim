package com.xinou.lawfrim.sso.shiro.exception;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.xinou.lawfrim.sso.config.ConfigSSO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By 信鸥科技
 * project : Innovation
 * Created by zhangbo on 17/10/25.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 * shiro 捕获异常
 */
@Slf4j
public class MyExceptionResolver implements HandlerExceptionResolver {


    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {


        ModelAndView mv = new ModelAndView();
            /*  使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常   */
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<>();

        //如果是 shiro
        if (ex instanceof UnauthorizedException) {
            attributes.put("recode", ConfigSSO.RE_CODE_PERMISSION_ERROR);
            attributes.put("remsg", ConfigSSO.RE_MSG_PERMISSION_ERROR + ":" + ex.getMessage());
        } else {
            //其他exception
            attributes.put("recode", ConfigSSO.RE_CODE_EXCEPTION_ERROR);
            attributes.put("remsg", ex.getMessage());
            log.error("catch exception : {}\r\nexception: ", ex.getMessage(),ex);
        }

        view.setAttributesMap(attributes);
        mv.setView(view);
        return mv;

    }

}
