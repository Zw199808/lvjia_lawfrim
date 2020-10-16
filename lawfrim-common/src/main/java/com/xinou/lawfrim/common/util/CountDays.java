package com.xinou.lawfrim.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by edz on 17/3/24.
 */
public class CountDays {


    public static int dateDiff(Timestamp toDate){

        int days;

        days = (int) ((toDate.getTime() - System.currentTimeMillis())/ (24 * 60 * 60 * 1000)+0.5);

        return  days;
    }



    //判断两个时间的大小 正数 第一个时间大
    public static int twoTimeDiff(Timestamp one,Timestamp two){

        int days;

        days = (int) ((one.getTime() - two.getTime())/ (24 * 60 * 60 * 1000));

        return  days;
    }


    /**
     * 判断当前时间是否在这两个时间中间
     * @param SDate
     * @param EDate
     * @return
     * @throws ParseException
     */
    public static boolean SEBetween(Timestamp SDate, Timestamp EDate) {

        SDate = getAddDate(SDate,-1);
        EDate = getAddDate(EDate,1);

        boolean res = false;

        int SDays=-1;
        int EDays=-1;

        Timestamp ts = new Timestamp(System.currentTimeMillis());

        SDays = Integer.parseInt(String.valueOf((SDate.getTime() - ts.getTime()) / (1000)));

        EDays = Integer.parseInt(String.valueOf( (EDate.getTime() - ts.getTime()) / (1000)));

        if(SDays<=0&EDays>0){
            res=true;
        }

        return res;
    }



    /**
     * 判断传入的时间与当前时间的大小
     * 正数 传入>当前时间   0  传入的时间 = 当前时间
     * 负数 传入<当前时间
     * @return
     */
    public static int isBigNowTime(Date time) {

        boolean res = false;

        Timestamp ts = new Timestamp(System.currentTimeMillis());

        int SDays = Integer.parseInt(String.valueOf((time.getTime() - ts.getTime()) / (1000)));


        return SDays;
    }




    public static double countHours(String t1){
        long time = System.currentTimeMillis();
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String t2 = format.format(time);
        Date d1 = null;
        Date d2 = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            d1 = sdf.parse(String.valueOf(t1));
            d2 = sdf.parse(t2);
        } catch (ParseException pe) {
//            System.out.println(pe.getMessage());
        }

        long dd1 = d1.getTime();
        long dd2 = d2.getTime();
        double hours = (double)(dd2-dd1)/3600/1000;

        return hours;
    }



    //计算两个时间相差多少分钟
    public static Long differentTime(Timestamp t1){


        Long toDate = t1.getTime();

        Long s = (toDate - System.currentTimeMillis()) / (1000 * 60);

        return s;

    }


    public static int dateTenS(Date fromDate, Date toDate) {
        int days;

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        days = (int) Math.abs((toDate.getTime() - fromDate.getTime())
                /1000);
        return days;
    }


    public static Timestamp getAddDate(Timestamp ts,int dayCount){


        // Date转为Calendar
//        Date date=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(TimeChange.TimeChangeDate(ts));

        calendar.add(Calendar.DATE, dayCount);

        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");

        String str2=s.format(calendar.getTime());

        str2 = str2+" 00:00:00";
        Timestamp endTime = TimeChange.stringChangeTime(str2);

//        // Calendar转为Date
//        Calendar ca=Calendar.getInstance();     //当前时间
//        Date d =(Date) ca.getTime();
//
//        System.out.println("java.util.date:toString()===:  "+d.toString());

        return endTime;
    }
//
//    public static Timestamp getAddDate(Timestamp ts,int dayCount){
//
//        System.out.println("----ts------"+ts);
//        System.out.println("----dayCount------"+dayCount);
//
//
//        int year = Integer.parseInt(TimeChange.timeFormat(ts,"yyyy"));
//        int month = Integer.parseInt(TimeChange.timeFormat(ts,"MM"));
//        int day = Integer.parseInt(TimeChange.timeFormat(ts,"dd"));
//
//        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
//
//        Calendar cld = Calendar.getInstance();
//        cld.set(Calendar.YEAR,year);
//        cld.set(Calendar.MONDAY,month);
//        cld.set(Calendar.DATE,day);
//
//        cld.add(Calendar.DATE, dayCount);
//        String str2=s.format(cld.getTime());
//
//        str2 = str2+" 00:00:00";
//
//        Timestamp endTime = TimeChange.stringChangeTime(str2);
//
//        return endTime;
//    }


    public static int compareDate(long before,long last){

        //last 晚
        if (before < last) {
            return 1;
            //last早
        } else if (before > last) {
            return -1;
        } else {//相等
            return 0;
        }
    }


    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            return day2-day1;
        }
    }

    // 查询当月有多少天
    public static int monthsDay(){
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }



    public static void main(String[] args){

    }




}
