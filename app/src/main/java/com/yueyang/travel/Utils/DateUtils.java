package com.yueyang.travel.Utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Yang on 2016/1/13.
 */
public class DateUtils {

    public static long calucateTime(String createAt) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String currentTimeStr = dateFormat.format(new Date());
        Calendar currentCalendar = Calendar.getInstance();
        Calendar createAtCalendar = Calendar.getInstance();

        currentCalendar.setTime(dateFormat.parse(currentTimeStr));
        createAtCalendar.setTime(dateFormat.parse(createAt));

        return (currentCalendar.getTimeInMillis() - createAtCalendar.getTimeInMillis()) / (1000 * 60 * 60);
    }

    public static String getStandardTime(String createAt){
        String replaceStr = createAt.replace("T"," ");
        return replaceStr.substring(0,19);
    }

    public static String getDateString(long hour){
        if(hour < 1){
            if (hour * 60 < 1){
                return "刚刚";
            }else {
                return hour * 60 + "分钟之前";
            }
        }else if (hour < 24){
            return hour + "小时之前";
        }else {
            return hour % 24 + "天之前";
        }
    }

}
