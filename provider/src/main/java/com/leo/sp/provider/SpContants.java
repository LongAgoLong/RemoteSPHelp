package com.leo.sp.provider;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class SpContants {

    public static final String CONTENT = "content://";
    public static String AUTHORITY = "";
    public static final String SEPARATOR = "/";
    public static String CONTENT_URI = "";
    public static final String TYPE_STRING = "string";
    public static final String TYPE_INT = "int";
    public static final String TYPE_LONG = "long";
    public static final String TYPE_FLOAT = "float";
    public static final String TYPE_BOOLEAN = "boolean";
    public static final String VALUE = "value";

    public static final String NULL_STRING = "null";
    public static final String TYPE_CONTAIN = "contain";
    public static final String TYPE_CLEAN = "clean";
    public static final String TYPE_GET_ALL = "get_all";

    public static final String CURSOR_COLUMN_NAME = "cursor_name";
    public static final String CURSOR_COLUMN_TYPE = "cursor_type";
    public static final String CURSOR_COLUMN_VALUE = "cursor_value";


    public static void initAuthority(Application application) {
        AUTHORITY = getMetaValue(application, "authority");
        CONTENT_URI = CONTENT + AUTHORITY;
    }

    public static String getMetaValue(Application application, String metaKey) {
        String metaValue = null;
        if (metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo appInfo = application.getPackageManager().getApplicationInfo(
                    application.getPackageName(), PackageManager.GET_META_DATA);
            metaValue = appInfo.metaData.getString(metaKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return metaValue;
    }
}
