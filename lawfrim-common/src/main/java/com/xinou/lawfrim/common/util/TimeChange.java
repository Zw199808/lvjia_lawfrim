package com.xinou.lawfrim.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: XGLLHZ
 * @date: 2020/5/27 下午5:27
 * @description:
 */
public class TimeChange {

    /**
     * 时间戳转字符串  yyyy-MM-dd HH:mm:ss格式
     * @param ts 时间戳
     * @return String
     */
    public static String timeChangeString(Timestamp ts){
        return  timeFormat(ts,"yyyy-MM-dd HH:mm:ss");
    }


    /**
     * 时间戳转字符串  yyyy-MM-dd HH:mm:ss格式
     * @param ts 时间戳
     * @return String
     */
    public static String timeChangeStringMDHMS(Timestamp ts){
        return  timeFormat(ts,"MM-dd HH:mm:ss");
    }


    /**
     * 时间戳转字符串 yyyy-MM-dd 格式
     * @param ts 时间戳
     * @return String
     */
    public static String timeStampChangeString(Timestamp ts){


        return  timeFormat(ts,"yyyy-MM-dd");
    }



    /**
     * 时间戳转字符串 yyyy-MM-dd 格式
     * @param ts 时间戳
     * @return String
     */
    public static String timeStampChangeStringYMD(Timestamp ts){


        return  timeFormat(ts,"yyyy年MM月dd日");
    }

    /**
     * 时间戳转字符串 yyyy年MM月dd日 HH:mm 格式
     * @param ts 时间戳
     * @return String
     */
    public static String timeStampChangStringDate(Timestamp ts){
        return timeFormat(ts,"yyyy年MM月dd日 HH:mm");
    }

    /**
     * 时间戳转字符串 yyyy年MM月dd日 HH:mm:ss 格式
     * @param ts 时间戳
     * @return String
     */
    public static String timeStampChangStringDateTime(Timestamp ts){
        return timeFormat(ts,"yyyy年MM月dd日 HH:mm:ss");
    }

    /**
     * 时间戳转字符串 yyyy-MM 格式
     * @param ts 时间戳
     * @return String
     */
    public static String TimeChangeStrYM(Timestamp ts){

        return  timeFormat(ts,"yyyy-MM");
    }


    /**
     * 时间格式的字符串
     * @param ts   时间戳
     * @param format 格式
     * @return
     */
    public static String timeFormat(Timestamp ts, String format){

        String tsStr = "";

        if (ts != null){

            DateFormat sdf = new SimpleDateFormat(format);
            try {
                tsStr = sdf.format(ts);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return tsStr;
    }


    /**
     * 字符串转时间戳
     * @param tsStr 时间字符串 yyyy-MM-dd HH:mm:ss
     * @return Timestamp
     */
    public static Timestamp stringChangeTime(String tsStr){
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        try {
            ts = Timestamp.valueOf(tsStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ts;
    }

    /**
     * 时间戳转Date
     * @param ts 时间戳
     * @return Date
     */
    public static Date TimeChangeDate(Timestamp ts){


        Date date = new Date();

        if (ts != null){

            try {
                date = ts;
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


        return date;

    }

    /**
     * Date转时间戳
     * @param date Date
     * @return Timestamp
     */
    public static Timestamp DateChangeTime(Date date){

        return  stringChangeTime(DateChangeString(date)) ;
    }

    /**
     * Date 转String
     * @param date Date
     * @return String
     */
    public static String DateChangeString(Date date){

        String dateStr = "";
        //format的格式可以任意
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH/mm/ss");
        try {
            dateStr = sdf.format(date);
//            System.out.println(dateStr);
//            dateStr = sdf2.format(date);
//            System.out.println(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateStr;
    }

    /**
     * Date 转String
     * @param date Date
     * @return String
     */
    public static String DateChangeString(Date date,String format){

        String dateStr = "";
        //format的格式可以任意
        DateFormat sdf = new SimpleDateFormat(format);
//        DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH/mm/ss");
        try {
            dateStr = sdf.format(date);
//            System.out.println(dateStr);
//            dateStr = sdf2.format(date);
//            System.out.println(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateStr;
    }









    /**
     * Date 转String
     * @param date Date
     * @return String
     */
    public static String DateChangeStringYMD(Date date){

        String dateStr = "";
        //format的格式可以任意
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH/mm/ss");
        try {
            dateStr = sdf.format(date);
//            System.out.println(dateStr);
//            dateStr = sdf2.format(date);
//            System.out.println(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateStr;
    }


    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public static String getTimeShort(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }


    /**
     * Date 转String
     * @param date Date
     * @return String
     */
    public static String DateChangeStringMD(Date date){

        String dateStr = "";
        //format的格式可以任意
        DateFormat sdf = new SimpleDateFormat("MM/dd");
//        DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH/mm/ss");
        try {
            dateStr = sdf.format(date);
//            System.out.println(dateStr);
//            dateStr = sdf2.format(date);
//            System.out.println(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateStr;
    }

    /**
     * String 转Date
     * @param dateStr String
     * @return Date
     */
    public static Timestamp StringChangeDate(String dateStr){

        Date date = new Date();
        //注意format的格式要与日期String的格式相匹配
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            date = sdf.parse(dateStr);
//            System.out.println(date.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Timestamp time = DateChangeTime(date);

        return time;
    }

    /**
     * 获取当前时间的时间字符串
     * @return
     */
    public static String getNowTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(date);

        return str;
    }
    /**
     * 获取当前时间的时间字符串
     * @return
     */
    public static String getNowTimeHMS(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String str = sdf.format(date);

        return str;
    }

    /**
     * 获取当前时间的时间字符串
     * @return
     */
    public static String getNowTimeYMDHMS(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);

        return str;
    }

    public static String GMTStr2Time(String dateStr){

        DateFormat formatFrom = new SimpleDateFormat("MMM dd,yyyy KK:mm:ss aa");
        Date date = null;
        try {
            date = formatFrom.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat formatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatTo.format(date);
    }




    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    public static Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }


    public static String getCurrentYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    public static int getSecondTimestamp(){

        Date date = new Date();

        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }

    //时间戳转时间   10位和13位
    public static String timestamp2Date(String str_num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (str_num.length() == 13) {
            String date = sdf.format(new Date(Long.parseLong(str_num)));
            return date;
        } else {
            String date = sdf.format(new Date(Integer.parseInt(str_num) * 1000L));
            return date;
        }
    }


    //十三位时间戳在转换成十位
    public static String ThirthTransTen(long time ){

        long timeStampSec = time/1000;
        String timestamp = String.format("%010d", timeStampSec);
        return timestamp;
    }

    //十位转换成十三位
    public static long TenTransThirth(long time ){

        String date = timestamp2Date(time+"");

        long transTime = stringChangeTime(date).getTime();
        return transTime;

    }

    public static void main(String[] args) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println();
    }

}
