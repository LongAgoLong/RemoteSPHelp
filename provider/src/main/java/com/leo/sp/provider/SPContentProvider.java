package com.leo.sp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * 依赖share存储的ContentProvider
 */
public class SPContentProvider extends ContentProvider {

    public static final int URI_TYPE_INDEX = 1;

    public static final int URI_TYPE_VALUE_KEY_INDEX = 2;

    @Override
    public boolean onCreate() {
        SpResolver.getInstance().init(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String[] path = uri.getPath().split(SpContants.SEPARATOR);
        String type = path[URI_TYPE_INDEX];
        if (type.equals(SpContants.TYPE_GET_ALL)) {
            Map<String, ?> allSp = SPImpl.getAll(getContext());
            if (allSp != null) {
                MatrixCursor cursor = new MatrixCursor(new String[]{SpContants.CURSOR_COLUMN_NAME,
                        SpContants.CURSOR_COLUMN_TYPE, SpContants.CURSOR_COLUMN_VALUE});
                Object[] rows = new Object[3];
                Set<String> ketSet = allSp.keySet();
                for (String key : ketSet) {
                    rows[0] = key;
                    rows[2] = allSp.get(key);

                    if (rows[2] instanceof Boolean) {
                        rows[1] = SpContants.TYPE_BOOLEAN;
                    } else if (rows[2] instanceof String) {
                        rows[1] = SpContants.TYPE_STRING;
                    } else if (rows[2] instanceof Integer) {
                        rows[1] = SpContants.TYPE_INT;
                    } else if (rows[2] instanceof Long) {
                        rows[1] = SpContants.TYPE_LONG;
                    } else if (rows[2] instanceof Float) {
                        rows[1] = SpContants.TYPE_FLOAT;
                    }
                    cursor.addRow(rows);
                }
                return cursor;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String[] path = uri.getPath().split(SpContants.SEPARATOR);
        String type = path[URI_TYPE_INDEX];
        String key = path[URI_TYPE_VALUE_KEY_INDEX];
        if (type.equals(SpContants.TYPE_CONTAIN)) {
            return SPImpl.contains(getContext(), key) + "";
        }
        return "" + SPImpl.get(getContext(), key, type);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (null == values) {
            return null;
        }
        String[] path = uri.getPath().split(SpContants.SEPARATOR);
        String type = path[1];
        String key = path[2];
        Object obj = values.get(SpContants.VALUE);
        if (obj != null) {
            SPImpl.save(getContext(), key, obj);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String[] path = uri.getPath().split(SpContants.SEPARATOR);
        String type = path[1];
        if (type.equals(SpContants.TYPE_CLEAN)) {
            SPImpl.clear(getContext());
            return 0;
        }
        String key = path[2];
        if (SPImpl.contains(getContext(), key)) {
            SPImpl.remove(getContext(), key);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        insert(uri, values);
        return 0;
    }
}
