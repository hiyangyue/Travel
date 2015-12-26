package com.yueyang.travel.Utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Yang on 2015/12/18.
 */
public class SnackbarUtils {

    public static void getSnackbar(View view,String message){
        Snackbar snackbar = Snackbar.make(view,message,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

}
