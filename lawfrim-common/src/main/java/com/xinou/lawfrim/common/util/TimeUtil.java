package com.xinou.lawfrim.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author: XGLLHZ
 * @date: 2020/5/12 上午10:48
 * @description:
 */
public class TimeUtil {

    public static long timeUTC(Timestamp timestamp){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long s = 0;
        try {
            s =  sdf.parse(timestamp.toString()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String timeToDate(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }
}
