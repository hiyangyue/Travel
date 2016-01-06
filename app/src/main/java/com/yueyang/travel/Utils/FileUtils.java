package com.yueyang.travel.Utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yang on 2016/1/6.
 */
public class FileUtils {

    public static File createImgFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_hhMMss").format(new Date());
        String imgFileName = "JPEG" + "_" + timeStamp + "_";
        //使用SD卡的标识
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        File file = File.createTempFile(
                imgFileName,
                ".jpg",
                storageDir
        );
        return file;
    }

}
