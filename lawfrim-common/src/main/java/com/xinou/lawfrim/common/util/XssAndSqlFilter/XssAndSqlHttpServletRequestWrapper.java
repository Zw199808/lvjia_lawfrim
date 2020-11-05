package com.xinou.lawfrim.common.util.XssAndSqlFilter;

/**
 * All rights Reserved, Designed By 信鸥科技
 * Created by xiao_XX on 2019/5/23.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 */

import com.alibaba.fastjson.JSON;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XssAndSqlHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static String key = "drop|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+" +
            "DROP|AND|EXEC|INSERT|SELECT|DELETE|UPDATE|COUNT|CHR|MID|MASTER|TRUNCATE|CHAR|DECLARE|OR";
    private static Set<String> notAllowedKeyWords = new HashSet<String>(0);
    private static String replacedString="INVALID";
    private static List<String> urllist = new ArrayList<>(); // 忽略接口

    static {
        String keyStr[] = key.split("\\|");
        for (String str : keyStr) {
            notAllowedKeyWords.add(str);
        }

        //  不需要过滤的接口
//        urllist.add("");
    }

    private String currentUrl;

    public XssAndSqlHttpServletRequestWrapper(HttpServletRequest servletRequest) {

//        ParamsRequestWrapper
        super(servletRequest);
        currentUrl = servletRequest.getRequestURI();
        Map<String, String[]> requestMap = servletRequest.getParameterMap();
        this.params.putAll(requestMap);
    }

    /********************************************/

    private Map<String, String[]> params = new HashMap<>();
    private static final String ENCODING = "UTF-8";
    private static final String CLASSTYPE = "java.lang.String";


    /**
     * 重写getInputStream方法  post请求参数必须通过流才能获取到值
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {

        ServletInputStream stream = super.getInputStream();

        //非json类型，直接返回
        if (!super.getHeader(HttpHeaders.CONTENT_TYPE).equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            return stream;
        }
        String json = IOUtils.toString(stream, ENCODING);

        json = replaceBlank(json);

        if (StringUtils.isBlank(json)) {
            return stream;
        }

//        System.out.println("转化前参数：" + json);
        Map<String, Object> map = modifyParams(json);
        System.out.println("转化后参数：" + JSON.toJSONString(map));

        ByteArrayInputStream bis = new ByteArrayInputStream(JSON.toJSONString(map).getBytes(ENCODING));

        return new ParamsServletInputStream(bis);
    }

    private  Map<String, Object> modifyParams(String json) {

        Map<String, Object> params = JSON.parseObject(json);
        Map<String, Object> maps = new HashMap<>(params.size());
        for (String key : params.keySet()) {
            Object value = getValue(params.get(key));
            maps.put(key, value);
        }
        return maps;
    }

    private  Object getValue(Object obj) {

        if (obj == null) {
            return null;
        }
        String type = obj.getClass().getName();
        // 对字符串的处理
        if (CLASSTYPE.equals(type)) {
            obj = cleanXSS(obj.toString());
        }
        return obj;
    }

    /********************************************/

    /**覆盖getParameter方法，将参数名和参数值都做xss过滤。
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }
        return encodedValues;
    }
    @Override
    public Map<String, String[]> getParameterMap(){
        Map<String, String[]> values=super.getParameterMap();
        if (values == null) {
            return null;
        }
        Map<String, String[]> result=new HashMap<>();
        for(String key:values.keySet()){
            String encodedKey=cleanXSS(key);
            int count=values.get(key).length;
            String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++){
                encodedValues[i]=cleanXSS(values.get(key)[i]);
            }
            result.put(encodedKey,encodedValues);
        }
        return result;
    }
    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }

    private  String cleanXSS(String valueP) {

        if (justifyStr(currentUrl)) {
            return valueP;
        }

        // You'll need to remove the spaces from the html entities below
        String value = valueP.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        value = cleanSqlKeyWords(value);

        // 过滤敏感字
//        value = SensitiveWord.filterFileStr(value);
        return value;
    }

    private String cleanSqlKeyWords(String value) {
        String paramValue = value;

        if (!justifyStr(currentUrl)) {

            for (String keyword : notAllowedKeyWords) {
                if ( paramValue.contains(keyword)) {
                    paramValue = StringUtils.replace(paramValue, keyword, replacedString);
                    log.error(this.currentUrl + "已被过滤，因为参数中包含不允许sql的关键词(" + keyword
                            + ")" + ";参数：" + value + ";过滤后的参数：" + paramValue);
                }
            }
        }
        return paramValue;
    }


    public static boolean justifyStr(String currentUrl){

        for (String url:urllist){
            if (currentUrl.contains(url)){
                return true;
            }
        }

        return false;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    public static void main(String[] args) {
    }

}