package com.xinou.lawfrim.web.util;

import cn.hutool.core.util.StrUtil;
import com.xinou.lawfrim.common.util.BusException;
import com.xinou.lawfrim.common.util.Config;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lizhongyuan
 */
public class HeadersUtil {

    public static Long getUserId(HttpServletRequest request){
        String userId =request.getAttribute("userId").toString();
        if (StrUtil.isEmpty(userId)){
            throw new BusException(Config.RE_CODE_PARAM_ERROR, Config.RE_MSG_PARAM_ERROR);
        }
        return Long.parseLong(userId);
    }

}
