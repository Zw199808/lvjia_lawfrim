package com.xinou.lawfrim.common.util.XssAndSqlFilter;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 信鸥科技
 * Created by xiao_XX on 2019/5/23.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 */
@Component
@WebFilter(urlPatterns = "/**", filterName = "XssAndSqlFilter",dispatcherTypes=DispatcherType.REQUEST)
public class XssAndSqlFilter implements Filter {


    private static final Logger logger = LoggerFactory.getLogger(XssAndSqlFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
//        System.out.println("进入过滤器");
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
//        //跨域设置
//        if(response instanceof HttpServletResponse){
//            HttpServletResponse httpServletResponse=(HttpServletResponse)response;
//            //通过在响应 header 中设置 ‘*’ 来允许来自所有域的跨域请求访问。
//            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//            //通过对 Credentials 参数的设置，就可以保持跨域 Ajax 时的 Cookie
//            //设置了Allow-Credentials，Allow-Origin就不能为*,需要指明具体的url域
//            //httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
//            //请求方式
//            httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
//            //（预检请求）的返回结果（即 Access-Control-Allow-Methods 和Access-Control-Allow-Headers 提供的信息） 可以被缓存多久
//            httpServletResponse.setHeader("Access-Control-Max-Age", "86400");
//            //首部字段用于预检请求的响应。其指明了实际请求中允许携带的首部字段
//            httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
//        }
        //sql,xss过滤
        HttpServletRequest httpServletRequest=(HttpServletRequest)request;

        List<String> urllist = new ArrayList<>();
        urllist.add("/webUtil/editor");

        for (String url:urllist){
            if (httpServletRequest.getRequestURI().contains(url)){
                chain.doFilter(request,response);
            }
        }

        logger.info("CrosXssFilter.......orignal url:{},ParameterMap:{}",httpServletRequest.getRequestURI(), JSONObject.toJSONString(httpServletRequest.getParameterMap()));
        XssAndSqlHttpServletRequestWrapper xssHttpServletRequestWrapper=new XssAndSqlHttpServletRequestWrapper(
                httpServletRequest);
        chain.doFilter(xssHttpServletRequestWrapper, response);
        logger.info("CrosXssFilter..........doFilter url:{},ParameterMap:{}",xssHttpServletRequestWrapper.getRequestURI(), JSONObject.toJSONString(xssHttpServletRequestWrapper.getParameterMap()));
    }
    @Override
    public void destroy() {

    }
}

