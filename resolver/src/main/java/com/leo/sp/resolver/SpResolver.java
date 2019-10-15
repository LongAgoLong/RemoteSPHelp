package com.leo.sp.resolver;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
/**
 * Created by LEO
 * On 2019/10/13
 */
public class SpResolver {
    private Context mContext;
    private String mAuthority;

    public SpResolver(Context mContext, String authority) {
        this.mContext = mContext;
        this.mAuthority = authority;
    }

    /**
     * int
     *
     * @param key
     * @return 默认返回值是0
     */
    public int getInt(String key) {
        int result = 0;
        Uri uri = ContentUris.withAppendedId(getUri(), SpConfigParams.INT);
        Cursor cursor = mContext.getContentResolver().query(uri, null, key, null, null);
        if (null != cursor) {
            if (cursor.moveToNext()) {
                result = cursor.getInt(cursor.getColumnIndex("data"));
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    public void setInt(String key, int value) {
        Uri uri = ContentUris.withAppendedId(getUri(), SpConfigParams.INT);
        ContentValues values = new ContentValues();
        values.put(key, value);
        ContentResolver contentResolver = mContext.getContentResolver();
        contentResolver.insert(uri, values);
        contentResolver.notifyChange(uri, null);
    }

    /**
     * long
     *
     * @param key
     * @return 默认返回值0
     */
    public long getLong(String key) {
        long result = 0L;
        Uri uri = ContentUris.withAppendedId(getUri(), SpConfigParams.LONE);
        Cursor cursor = mContext.getContentResolver().query(uri, null, key, null, null);
        if (null != cursor) {
            if (cursor.moveToNext()) {
                result = cursor.getLong(cursor.getColumnIndex("data"));
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    public void setLong(String key, long value) {
        Uri uri = ContentUris.withAppendedId(getUri(), SpConfigParams.LONE);
        ContentValues values = new ContentValues();
        values.put(key, value);
        ContentResolver contentResolver = mContext.getContentResolver();
        contentResolver.insert(uri, values);
        contentResolver.notifyChange(uri, null);
    }

    /**
     * float
     *
     * @param key
     * @return 默认返回值0
     */
    public float getFloat(String key) {
        float result = 0;
        Uri uri = ContentUris.withAppendedId(getUri(), SpConfigParams.FLOAT);
        Cursor cursor = mContext.getContentResolver().query(uri, null, key, null, null);
        if (null != cursor) {
            if (cursor.moveToNext()) {
                result = cursor.getFloat(cursor.getColumnIndex("data"));
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    public void setFloat(String key, float value) {
        Uri uri = ContentUris.withAppendedId(getUri(), SpConfigParams.FLOAT);
        ContentValues values = new ContentValues();
        values.put(key, value);
        ContentResolver contentResolver = mContext.getContentResolver();
        contentResolver.insert(uri, values);
        contentResolver.notifyChange(uri, null);
    }

    /**
     * boolean
     * 用int来实现boolean
     *
     * @param key
     * @return 默认返回值false
     */
    public boolean getBoolean(String key) {
        boolean result = false;
        Uri uri = ContentUris.withAppendedId(getUri(), SpConfigParams.INT);
        Cursor cursor = mContext.getContentResolver().query(uri, null, key, null, null);
        if (null != cursor) {
            if (cursor.moveToNext()) {
                int b = cursor.getInt(cursor.getColumnIndex("data"));
                result = b == 1;
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    public void setBoolean(String key, boolean value) {
        Uri uri = ContentUris.withAppendedId(getUri(), SpConfigParams.INT);
        ContentValues values = new ContentValues();
        values.put(key, value ? 1 : 0);
        ContentResolver contentResolver = mContext.getContentResolver();
        contentResolver.insert(uri, values);
        contentResolver.notifyChange(uri, null);
    }

    /**
     * String
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        String result = "";
        Uri uri = ContentUris.withAppendedId(getUri(), SpConfigParams.STRING);
        Cursor cursor = mContext.getContentResolver().query(uri, null, key, null, null);
        if (null != cursor) {
            if (cursor.moveToNext()) {
                result = cursor.getString(cursor.getColumnIndex("data"));
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    public void setString(String key, String value) {
        Uri uri = ContentUris.withAppendedId(getUri(), SpConfigParams.STRING);
        ContentValues values = new ContentValues();
        values.put(key, value);
        ContentResolver contentResolver = mContext.getContentResolver();
        contentResolver.insert(uri, values);
        contentResolver.notifyChange(uri, null);
    }

    private Uri getUri() {
        String url = SpConfigParams.PREFIX + SpConfigParams.prefix_suffix
                + mAuthority + SpConfigParams.separator + SpConfigParams.PATH;
        return Uri.parse(url);
    }
}
