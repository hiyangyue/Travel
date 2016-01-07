package com.yueyang.travel.Utils;

/**
 * Created by Yang on 2016/1/6.
 */
public class TimeUtils {

    public static String pickUpTime(String createTime){
        String replaceTime = createTime.replace("T",",");
        return replaceTime.substring(0,13);
    }

}
