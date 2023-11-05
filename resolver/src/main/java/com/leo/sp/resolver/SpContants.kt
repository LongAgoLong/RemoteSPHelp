package com.leo.sp.resolver

import android.content.Context
import android.content.pm.PackageManager

object SpContants {
    const val CONTENT = "content://"
    var AUTHORITY: String? = ""
    const val SEPARATOR = "/"
    var CONTENT_URI = ""
    const val TYPE_STRING = "string"
    const val TYPE_INT = "int"
    const val TYPE_LONG = "long"
    const val TYPE_FLOAT = "float"
    const val TYPE_DOUBLE = "double"
    const val TYPE_BOOLEAN = "boolean"
    const val VALUE = "value"
    const val NULL_STRING = "null"
    const val TYPE_CONTAIN = "contain"
    const val TYPE_CLEAN = "clean"
    const val TYPE_GET_ALL = "get_all"
    const val CURSOR_COLUMN_NAME = "cursor_name"
    const val CURSOR_COLUMN_TYPE = "cursor_type"
    const val CURSOR_COLUMN_VALUE = "cursor_value"
    fun initAuthority(context: Context) {
        AUTHORITY = getMetaValue(context, "authority")
        CONTENT_URI = CONTENT + AUTHORITY
    }

    fun getMetaValue(context: Context, metaKey: String?): String? {
        var metaValue: String? = null
        if (metaKey == null) {
            return null
        }
        try {
            val appInfo = context.packageManager.getApplicationInfo(
                context.packageName, PackageManager.GET_META_DATA
            )
            metaValue = appInfo.metaData.getString(metaKey)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return metaValue
    }
}
