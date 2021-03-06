package com.yueyang.travel.domin.Utils;

import android.util.Log;

/**
 * Created by Yang on 2015/12/24.
 */
public class DBug {

    private final static boolean DEBUG = true;

    public static void e(String tag,String msg){
        if(DEBUG){
            Log.e(tag, msg);
        }
    }
    public static void d(String tag,String msg){
        if(DEBUG){
            Log.d(tag,msg);
        }
    }
    public static void i(String tag,String msg){
        if(DEBUG){
            Log.i(tag,msg);
        }
    }
    public static void v(String tag,String msg){
        if(DEBUG){
            Log.v(tag,msg);
        }
    }
    public static void w(String tag,String msg){
        if(DEBUG){
            Log.w(tag,msg);
        }
    }

}
