package com.leo.sp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.leo.sp.provider.SpConfigParams.AUTHORITY;
import static com.leo.sp.provider.SpConfigParams.PATH;

/**
 * Created by LEO
 * On 2019/10/13
 * Description:
 * 内容URI
 * <prefix>://<authority>/<data_type>/<id>
 * prefix	    前缀：一直被设置为content://
 * authority	授权：指定内容提供者的名称，例如联系人，浏览器等。第三方的内容提供者可以是全名，如：cn.programmer.statusprovider
 * path	数据类型：这个表明这个特殊的内容提供者中的数据的类型。例如：你要通过内容提供者Contacts来获取所有的通讯录，数据路径是people，那么URI将是下面这样：content://contacts/people
 * id	        这个指定特定的请求记录。例如：你在内容提供者Contacts中查找联系人的ID号为5，那么URI看起来是这样：content://contacts/people/5
 */
public class SpProvider extends ContentProvider {
    private Context mContext;
    private static final int CONFIG = 1;

    private UriMatcher mUriMatcher;
    private SharedPreferences preferences;

    private MatrixCursor mMatrixCursor;

    @Override
    public boolean onCreate() {
        mContext = getContext();
        if (null == mContext) {
            throw new RuntimeException("context is null");
        }
        /**
         * 在AndroidManifest.xml中配置
         */
        preferences = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        SpConfigParams.AUTHORITY = getMetaValue(SpConfigParams.AUTHORITY_KEY);

        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, PATH, CONFIG);
        return true;
    }

    /**
     * @param uri
     * @param projection    null
     * @param selection     key
     * @param selectionArgs null
     * @param sortOrder     null
     * @return
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int code = mUriMatcher.match(uri);
        switch (code) {
            case CONFIG:
                long type = ContentUris.parseId(uri);
                if (TextUtils.isEmpty(selection)) {
                    throw new IllegalArgumentException("selection is null");
                }
                mMatrixCursor = new MatrixCursor(new String[]{"data"}, 1);
                if (type == SpConfigParams.INT) {
                    mMatrixCursor.addRow(new Object[]{preferences.getInt(selection, 0)});
                } else if (type == SpConfigParams.LONE) {
                    mMatrixCursor.addRow(new Object[]{preferences.getLong(selection, 0)});
                } else if (type == SpConfigParams.FLOAT) {
                    mMatrixCursor.addRow(new Object[]{preferences.getFloat(selection, 0)});
                } else if (type == SpConfigParams.BOOLEAN) {
                    mMatrixCursor.addRow(new Object[]{preferences.getBoolean(selection, false)});
                } else if (type == SpConfigParams.STRING) {
                    mMatrixCursor.addRow(new Object[]{preferences.getString(selection, null)});
                } else {
                    mMatrixCursor.addRow(new Object[]{preferences.getString(selection, null)});
                }
                return mMatrixCursor;
            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (null == contentValues) {
            return null;
        }
        int code = mUriMatcher.match(uri);
        switch (code) {
            case CONFIG:
                String selection = contentValues.getAsString("selection");
                if (TextUtils.isEmpty(selection)) {
                    break;
                }
                Object content = contentValues.get("data");
                if (content instanceof Integer) {
                    preferences.edit().putInt(selection, (int) content).commit();
                } else if (content instanceof Long) {
                    preferences.edit().putLong(selection, (long) content).commit();
                } else if (content instanceof Float) {
                    preferences.edit().putFloat(selection, (float) content).commit();
                } else if (content instanceof Boolean) {
                    preferences.edit().putBoolean(selection, (boolean) content).commit();
                } else if (content instanceof String) {
                    preferences.edit().putString(selection, (String) content).commit();
                } else {
                    throw new IllegalArgumentException("UnSupport content type : " + content);
                }
                break;
            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (TextUtils.isEmpty(selection)) {
            return 0;
        }
        int code = mUriMatcher.match(uri);
        switch (code) {
            case CONFIG:
                preferences.edit().remove(selection).commit();
                return 1;
            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }

    /**
     * 获取清单文件中meta值
     * 值不可以为纯数字，要不然会报错
     *
     * @param metaKey
     * @return
     */
    private String getMetaValue(String metaKey) {
        String metaValue = null;
        if (metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo appInfo = mContext.getPackageManager().getApplicationInfo(
                    mContext.getPackageName(), PackageManager.GET_META_DATA);
            metaValue = appInfo.metaData.getString(metaKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return metaValue;
    }
}
