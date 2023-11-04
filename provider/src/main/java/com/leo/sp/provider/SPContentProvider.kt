package com.leo.sp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.annotation.Keep
import com.leo.sp.provider.datastore.SpDataStoreImpl
import kotlin.reflect.KClass

/**
 * 依赖share存储的ContentProvider
 */
@Keep
class SPContentProvider : ContentProvider() {
    private lateinit var mCtx: Context
    override fun onCreate(): Boolean {
        mCtx = context!!
        SpContants.initAuthority(mCtx)
        SpResolver.getInstance().init(mCtx)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        val path =
            getSafePath(uri).split(SpContants.SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
        val type = path[URI_TYPE_INDEX]
        val key = path[URI_TYPE_VALUE_KEY_INDEX]
        return if (type == SpContants.TYPE_CONTAIN) {
            SpDataStoreImpl.containsSync(mCtx, key).toString()
        } else {
            val value = SpDataStoreImpl.getSync(mCtx, key, getTypeClass(type))
            return value.toString()
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (null == values) {
            return null
        }
        val path =
            getSafePath(uri).split(SpContants.SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
        val type = path[1]
        val key = path[2]
        val obj = values[SpContants.VALUE]
        if (obj != null) {
            SpDataStoreImpl.put(mCtx, key, obj, getTypeClass(type))
        }
        safeNotifyChange(uri, null)
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val path =
            getSafePath(uri).split(SpContants.SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
        val type = path[1]
        if (type == SpContants.TYPE_CLEAN) {
            SpDataStoreImpl.clear(mCtx)
            return 0
        }
        val key = path[2]
        SpDataStoreImpl.remove(mCtx, key, getTypeClass(type))
        safeNotifyChange(uri, null)
        return 0
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        insert(uri, values)
        return 0
    }

    private fun getTypeClass(type: String): KClass<out Any> {
        return when (type) {
            SpContants.TYPE_STRING -> String::class
            SpContants.TYPE_INT -> Int::class
            SpContants.TYPE_LONG -> Long::class
            SpContants.TYPE_FLOAT -> Float::class
            SpContants.TYPE_DOUBLE -> Double::class
            SpContants.TYPE_BOOLEAN -> Boolean::class
            else -> throw RuntimeException("getTypeClass: not support type -> $type")
        }
    }

    private fun getSafePath(uri: Uri): String {
        return if (uri.path == null) "" else uri.path!!
    }

    private fun safeNotifyChange(uri: Uri, observer: ContentObserver?) {
        val contentResolver = mCtx.contentResolver
        if (contentResolver == null) {
            Log.e(TAG, "safeNotifyChange: contentResolver is null.")
            return
        }
        contentResolver.notifyChange(uri, observer)
    }

    companion object {
        private const val TAG = "SPContentProvider"
        const val URI_TYPE_INDEX = 1
        const val URI_TYPE_VALUE_KEY_INDEX = 2
    }
}
