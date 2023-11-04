package com.leo.sp.resolver;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class SpContants {

    // normal constants
    public static final String CONTENT = "content://";
    public static String AUTHORITY = "";
    public static final String SEPARATOR = "/";
    public static String CONTENT_URI = "";
    public static final String TYPE_STRING = "string";
    public static final String TYPE_INT = "int";
    public static final String TYPE_LONG = "long";
    public static final String TYPE_FLOAT = "float";
    public static final String TYPE_DOUBLE = "double";
    public static final String TYPE_BOOLEAN = "boolean";
    public static final String VALUE = "value";

    public static final String NULL_STRING = "null";
    public static final String TYPE_CONTAIN = "contain";
    public static final String TYPE_CLEAN = "clean";


    public static void initAuthority(Context context) {
        AUTHORITY = getMetaValue(context, "authority");
        CONTENT_URI = CONTENT + AUTHORITY;
    }

    private static String getMetaValue(Context context, String metaKey) {
        String metaValue = null;
        if (metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            metaValue = appInfo.metaData.getString(metaKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return metaValue;
    }
}
