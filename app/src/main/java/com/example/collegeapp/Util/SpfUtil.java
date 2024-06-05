package com.example.collegeapp.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class SpfUtil {
    private static String SPF_NAME="noteSpf";

    public static void saveString(Context context,String key,String value){

        //小数据存储
        SharedPreferences spf =context.getSharedPreferences(SPF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context,String key) {
        SharedPreferences spf = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
        return spf.getString(key, "");
    }

    public static void saveInt(Context context,String key,int value){
        SharedPreferences spf =context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public static int getInt(Context context,String key) {
        SharedPreferences spf = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
        return spf.getInt(key, -1);
    }

    public static int getIntWithDefault(Context context,String key,int defValue) {
        SharedPreferences spf = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
        return spf.getInt(key, defValue);
    }
}
