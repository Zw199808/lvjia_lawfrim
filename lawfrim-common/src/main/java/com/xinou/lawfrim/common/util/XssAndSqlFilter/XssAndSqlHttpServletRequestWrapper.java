package com.xinou.lawfrim.common.util.XssAndSqlFilter;

/**
 * All rights Reserved, Designed By 信鸥科技
 * Created by xiao_XX on 2019/5/23.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 */

import cn.hutool.extra.emoji.EmojiUtil;
import com.alibaba.fastjson.JSON;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.BusException;
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

        //判断接口是否需要过滤
        if (justifyStr(currentUrl)) {
            return valueP;
        }

        // You'll need to remove the spaces from the html entities below
//        String value = valueP.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
//        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
//        value = value.replaceAll("'", "& #39;");
//        value = value.replaceAll("eval\\((.*)\\)", "");
//        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
//        value = value.replaceAll("script", "");
//        value = cleanSqlKeyWords(value);

        //String str="判断的字符串@";
//        特殊字符抛出异常
        String regEx="[`@#$%^&*()+=|{}\\[\\]<>/@#￥%&*（）+|{}【】]";
        Pattern p=Pattern.compile(regEx);
        Matcher m=p.matcher(valueP);
        //System.out.println(m.find());
        if (m.find()){
            throw new BusException(Config.RE_STRING_IS_SPECIAL_CODE,Config.RE_STRING_IS_SPECIAL_MSG);
        }

//      包含js抛出异常
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(valueP);
        if (m_script.find()){
            throw new BusException(Config.RE_STRING_IS_WORD_CODE,Config.RE_STRING_IS_WORD_CODE);
        }
        //valueP=m_script.replaceAll(""); //过滤

        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(valueP);
        if (m_style.find()){
            throw new BusException(Config.RE_STRING_IS_WORD_CODE,Config.RE_STRING_IS_WORD_CODE);
        }

        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(valueP);
        if (m_style.find()){
            throw new BusException(Config.RE_STRING_IS_HTML_CODE,Config.RE_STRING_IS_HTML_MSG);
        }

//        过滤敏感字
        //value = SensitiveWord.filterFileStr(value);
//      判断是否存在sql语句
        cleanSqlKeyWords(valueP);
//      判断是否存在emoji
        cleanEmojiKeyWords(valueP);
        return valueP;
    }

    private String cleanSqlKeyWords(String value) {
        String paramValue = value;
        if (!justifyStr(currentUrl)) {

            for (String keyword : notAllowedKeyWords) {
                if ( paramValue.contains(keyword)) {
//                    paramValue = StringUtils.replace(paramValue, keyword, replacedString);
//                    log.error(this.currentUrl + "已被过滤，因为参数中包含不允许sql的关键词(" + keyword
//                            + ")" + ";参数：" + value + ";过滤后的参数：" + paramValue);
                    throw new BusException(Config.RE_STRING_IS_WORD_CODE,Config.RE_STRING_IS_WORD_MSG);
                }
            }
        }
        return paramValue;
    }

    private String cleanEmojiKeyWords(String value) {
        if (EmojiUtil.containsEmoji(value)) {
            throw new BusException(Config.RE_STRING_IS_EMOJI_CODE,Config.RE_STRING_IS_EMOJI_MSG);
        }
//        String paramValue = value;
//
//        if (!justifyStr(currentUrl)) {
//            int len = paramValue.length();
//            for (int i = 0; i < len; i++) {
//                char hs = paramValue.charAt(i);
//                if (0xd800 <= hs && hs <= 0xdbff) {
//                    if (paramValue.length() > 1) {
//                        char ls = paramValue.charAt(i + 1);
//                        int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
//                        if (0x1d000 <= uc && uc <= 0x1f77f) {
//                            throw new BusException(Config.RE_STRING_IS_EMOJI_CODE,Config.RE_STRING_IS_EMOJI_MSG);
//                        }
//                    }
//                } else {
//                    // non surrogate
//                    if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
//                        throw new BusException(Config.RE_STRING_IS_EMOJI_CODE,Config.RE_STRING_IS_EMOJI_MSG);
//                    } else if (0x2B05 <= hs && hs <= 0x2b07) {
//                        throw new BusException(Config.RE_STRING_IS_EMOJI_CODE,Config.RE_STRING_IS_EMOJI_MSG);
//                    } else if (0x2934 <= hs && hs <= 0x2935) {
//                        throw new BusException(Config.RE_STRING_IS_EMOJI_CODE,Config.RE_STRING_IS_EMOJI_MSG);
//                    } else if (0x3297 <= hs && hs <= 0x3299) {
//                        throw new BusException(Config.RE_STRING_IS_EMOJI_CODE,Config.RE_STRING_IS_EMOJI_MSG);
//                    } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
//                            || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
//                            || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
//                        throw new BusException(Config.RE_STRING_IS_EMOJI_CODE,Config.RE_STRING_IS_EMOJI_MSG);
//                    }
//                    if (!false && paramValue.length() > 1 && i < paramValue.length() - 1) {
//                        char ls = paramValue.charAt(i + 1);
//                        if (ls == 0x20e3) {
//                            throw new BusException(Config.RE_STRING_IS_EMOJI_CODE,Config.RE_STRING_IS_EMOJI_MSG);
//                        }
//                    }
//                }
//            }
//            return paramValue;
//        }
        return value;
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