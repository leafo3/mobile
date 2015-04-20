package com.leafo3.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alberto Rubalcaba on 4/11/2015.
 */
public class EnvironmentUtils {

    private static final String PREFS_NAME = "com.spaceapps.main.shared.name";
    private static final String ID_NAME = "com.spaceapps.main.shared.userid";
    public static final boolean TEST = true;

    /**
     * Change this URL to point to any production server,
     * this is only for testing, you can create your own static variable with
     * your server url and set it at doInBackground function of any BaseAsyncTask you create
     *
     */
    private static final String TEST_HOST = "http://192.168.0.10";
    private static final int TEST_PORT = 8080;

    private static final String EMAIL_PREFS = "com.spaceapps.main.shared.email";
    public static final String TEST_URL = TEST_HOST + ":" + TEST_PORT;

    public static void saveUserEmail(Context context, String email){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(EMAIL_PREFS, email).commit();
    }

    public static String getUserEmail(Context context){
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(EMAIL_PREFS, "");
    }

    public static void saveUserId(Context context, String id){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(ID_NAME, id).commit();
    }

    public static String getUserId(Context context){
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(ID_NAME, "");
    }

    public static String getNewPicturePath(){
        String directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/leafo3/";
        return directory += "leaf_" + new SimpleDateFormat("MM-dd-yyyy HH-mm").format(new Date()) + ".jpg";
    }


}
