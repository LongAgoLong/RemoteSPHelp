package com.leo.sp.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import java.lang.ref.WeakReference;

public class SpResolver {
    private static SpResolver mInstance;
    private static final Object mLock = new Object();
    private WeakReference<Context> contextWeakReference;

    private SpResolver() {
    }

    public static SpResolver getInstance() {
        if (null == mInstance) {
            synchronized (mLock) {
                if (null == mInstance) {
                    mInstance = new SpResolver();
                }
            }
        }
        return mInstance;
    }

    private Context getContext() {
        if (null == contextWeakReference) {
            throw new IllegalStateException("context has not been initialed");
        }
        Context context = contextWeakReference.get();
        if (context == null) {
            throw new IllegalStateException("context has not been initialed");
        }
        return context;
    }

    public void setContext(Context mContext) {
        contextWeakReference = new WeakReference<>(mContext);
    }

    public void save(String name, Boolean t) {
        Context context = getContext();
        ContentResolver cr = context.getContentResolver();
        synchronized (mLock) {
            Uri uri = Uri.parse(SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_BOOLEAN + SpContants.SEPARATOR + name);
            ContentValues cv = new ContentValues();
            cv.put(SpContants.VALUE, t);
            cr.update(uri, cv, null, null);
            cr.notifyChange(uri, null);
        }
    }

    public void save(String name, String t) {
        Context context = getContext();
        ContentResolver cr = context.getContentResolver();
        synchronized (mLock) {
            Uri uri = Uri.parse(SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_STRING + SpContants.SEPARATOR + name);
            ContentValues cv = new ContentValues();
            cv.put(SpContants.VALUE, t);
            cr.update(uri, cv, null, null);
            cr.notifyChange(uri, null);
        }
    }

    public void save(String name, Integer t) {
        Context context = getContext();
        ContentResolver cr = context.getContentResolver();
        synchronized (mLock) {
            Uri uri = Uri.parse(SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_INT + SpContants.SEPARATOR + name);
            ContentValues cv = new ContentValues();
            cv.put(SpContants.VALUE, t);
            cr.update(uri, cv, null, null);
            cr.notifyChange(uri, null);
        }
    }

    public void save(String name, Long t) {
        Context context = getContext();
        ContentResolver cr = context.getContentResolver();
        synchronized (mLock) {
            Uri uri = Uri.parse(SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_LONG + SpContants.SEPARATOR + name);
            ContentValues cv = new ContentValues();
            cv.put(SpContants.VALUE, t);
            cr.update(uri, cv, null, null);
            cr.notifyChange(uri, null);
        }
    }

    public void save(String name, Float t) {
        Context context = getContext();
        ContentResolver cr = context.getContentResolver();
        synchronized (mLock) {
            Uri uri = Uri.parse(SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_BOOLEAN + SpContants.SEPARATOR + name);
            ContentValues cv = new ContentValues();
            cv.put(SpContants.VALUE, t);
            cr.update(uri, cv, null, null);
            cr.notifyChange(uri, null);
        }
    }


    public String getString(String name, String defaultValue) {
        Context context = getContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(SpContants.CONTENT_URI + SpContants.SEPARATOR
                + SpContants.TYPE_STRING + SpContants.SEPARATOR + name);
        String rtn = cr.getType(uri);
        if (rtn == null || rtn.equals(SpContants.NULL_STRING)) {
            return defaultValue;
        }
        return rtn;
    }

    public int getInt(String name, int defaultValue) {
        Context context = getContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(SpContants.CONTENT_URI + SpContants.SEPARATOR
                + SpContants.TYPE_INT + SpContants.SEPARATOR + name);
        String rtn = cr.getType(uri);
        if (rtn == null || rtn.equals(SpContants.NULL_STRING)) {
            return defaultValue;
        }
        return Integer.parseInt(rtn);
    }

    public float getFloat(String name, float defaultValue) {
        Context context = getContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(SpContants.CONTENT_URI + SpContants.SEPARATOR
                + SpContants.TYPE_FLOAT + SpContants.SEPARATOR + name);
        String rtn = cr.getType(uri);
        if (rtn == null || rtn.equals(SpContants.NULL_STRING)) {
            return defaultValue;
        }
        return Float.parseFloat(rtn);
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        Context context = getContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(SpContants.CONTENT_URI + SpContants.SEPARATOR
                + SpContants.TYPE_BOOLEAN + SpContants.SEPARATOR + name);
        String rtn = cr.getType(uri);
        if (rtn == null || rtn.equals(SpContants.NULL_STRING)) {
            return defaultValue;
        }
        return Boolean.parseBoolean(rtn);
    }

    public long getLong(String name, long defaultValue) {
        Context context = getContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(SpContants.CONTENT_URI + SpContants.SEPARATOR
                + SpContants.TYPE_LONG + SpContants.SEPARATOR + name);
        String rtn = cr.getType(uri);
        if (rtn == null || rtn.equals(SpContants.NULL_STRING)) {
            return defaultValue;
        }
        return Long.parseLong(rtn);
    }

    public boolean contains(String name) {
        Context context = getContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(SpContants.CONTENT_URI + SpContants.SEPARATOR
                + SpContants.TYPE_CONTAIN + SpContants.SEPARATOR + name);
        String rtn = cr.getType(uri);
        if (rtn == null || rtn.equals(SpContants.NULL_STRING)) {
            return false;
        } else {
            return Boolean.parseBoolean(rtn);
        }
    }

    public void remove(String name) {
        Context context = getContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(SpContants.CONTENT_URI + SpContants.SEPARATOR
                + SpContants.TYPE_LONG + SpContants.SEPARATOR + name);
        cr.delete(uri, null, null);
    }

    public void clear() {
        Context context = getContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri =
                Uri.parse(SpContants.CONTENT_URI + SpContants.SEPARATOR + SpContants.TYPE_CLEAN);
        cr.delete(uri, null, null);
    }
}