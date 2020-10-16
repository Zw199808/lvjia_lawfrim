package com.xinou.lawfrim.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.xinou.lawfrim.common.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 信鸥科技
 * Created by xiao_XX on 2019/10/22.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 */
public class AnnotationResolver {

    private static AnnotationResolver resolver;


    public static AnnotationResolver newInstance() {

        if (resolver == null) {
            return resolver = new AnnotationResolver();
        } else {
            return resolver;
        }

    }


    /**
     * 解析注解上的值
     *
     * @param joinPoint
     * @param str       需要解析的字符串
     * @return
     */
    public Object resolver(JoinPoint joinPoint, String str) {

        if (str == null) {
            return null;
        }

        Object value = null;
        if (str.matches("#\\{\\D*\\}")) {// 如果name匹配上了#{},则把内容当作变量
            String newStr = str.replaceAll("#\\{", "").replaceAll("\\}", "");
            if (newStr.contains(".")) { // 复杂类型
                try {
                    value = complexResolver(joinPoint, newStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                value = simpleResolver(joinPoint, newStr);
            }
        } else { //非变量
            value = str;
        }
        return value;
    }


    private Object complexResolver(JoinPoint joinPoint, String str) throws Exception {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String[] names = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        String [] moreParam = str.split(",");
        List<String> strs = new ArrayList<>();
        String entityName = "";
        for (int k=0;k<moreParam.length;k++){
            String[] value = moreParam[k].split("\\.");
            entityName = value[0];
            strs.add(value[1]);
        }
        for (int i = 0; i < names.length; i++) {
            if (entityName.equals(names[i])) {
                Object obj = args[i];
                JSONObject json= (JSONObject) JSONObject.toJSON(obj);
//                Method dmethod = obj.getClass().getDeclaredMethod(getMethodName(strs[1]), (Class<?>) null);
//                Object value = dmethod.invoke(args[i]);
//                return getValue(value, 1, strs)
                if (strs.size() == 1){
                    return json.getString(strs.get(0));
                }
                if (!StringUtil.isNullString(json.getString(strs.get(0)))){
                    return "账号："+json.getString(strs.get(0));
                }
                return "ic卡号："+json.getString(strs.get(1));
            }
        }

        return null;

    }

    private Object getValue(Object obj, int index, String[] strs) {

        try {
            if (obj != null && index < strs.length - 1) {
                Method method = obj.getClass().getDeclaredMethod(getMethodName(strs[index + 1]), (Class<?>) null);
                obj = method.invoke(obj);
                getValue(obj, index + 1, strs);
            }

            return obj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getMethodName(String name) {
        return "get" + name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
    }


    private Object simpleResolver(JoinPoint joinPoint, String str) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] names = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < names.length; i++) {
            if (str.equals(names[i])) {
                return args[i];
            }
        }
        return null;
    }
}

