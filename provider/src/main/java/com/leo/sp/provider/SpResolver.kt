package com.leo.sp.provider

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import java.lang.ref.WeakReference

class SpResolver private constructor() {
    private var contextWeakReference: WeakReference<Context>? = null
    private val context: Context
        get() {
            checkNotNull(contextWeakReference) { "context has not been initialed" }
            return contextWeakReference!!.get()
                ?: throw IllegalStateException("context has not been initialed")
        }

    fun init(mContext: Context) {
        contextWeakReference = WeakReference(mContext)
    }

    fun save(name: String, t: Boolean?) {
        val context = context
        val cr = context.contentResolver
        synchronized(mLock) {
            val uri = Uri.parse(
                SpContants.CONTENT_URI + SpContants.SEPARATOR
                        + SpContants.TYPE_BOOLEAN + SpContants.SEPARATOR + name
            )
            val cv = ContentValues()
            cv.put(SpContants.VALUE, t)
            cr.update(uri, cv, null, null)
        }
    }

    fun save(name: String, t: String?) {
        val context = context
        val cr = context.contentResolver
        synchronized(mLock) {
            val uri = Uri.parse(
                SpContants.CONTENT_URI + SpContants.SEPARATOR
                        + SpContants.TYPE_STRING + SpContants.SEPARATOR + name
            )
            val cv = ContentValues()
            cv.put(SpContants.VALUE, t)
            cr.update(uri, cv, null, null)
        }
    }

    fun save(name: String, t: Int?) {
        val context = context
        val cr = context.contentResolver
        synchronized(mLock) {
            val uri = Uri.parse(
                SpContants.CONTENT_URI + SpContants.SEPARATOR
                        + SpContants.TYPE_INT + SpContants.SEPARATOR + name
            )
            val cv = ContentValues()
            cv.put(SpContants.VALUE, t)
            cr.update(uri, cv, null, null)
        }
    }

    fun save(name: String, t: Long?) {
        val context = context
        val cr = context.contentResolver
        synchronized(mLock) {
            val uri = Uri.parse(
                SpContants.CONTENT_URI + SpContants.SEPARATOR
                        + SpContants.TYPE_LONG + SpContants.SEPARATOR + name
            )
            val cv = ContentValues()
            cv.put(SpContants.VALUE, t)
            cr.update(uri, cv, null, null)
        }
    }

    fun save(name: String, t: Float?) {
        val context = context
        val cr = context.contentResolver
        synchronized(mLock) {
            val uri = Uri.parse(
                SpContants.CONTENT_URI + SpContants.SEPARATOR
                        + SpContants.TYPE_FLOAT + SpContants.SEPARATOR + name
            )
            val cv = ContentValues()
            cv.put(SpContants.VALUE, t)
            cr.update(uri, cv, null, null)
        }
    }

    fun getString(name: String, defaultValue: String): String {
        val context = context
        val cr = context.contentResolver
        val uri = Uri.parse(
            SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_STRING + SpContants.SEPARATOR + name
        )
        val rtn = cr.getType(uri)
        return if (rtn == null || rtn == SpContants.NULL_STRING) {
            defaultValue
        } else rtn
    }

    fun getInt(name: String, defaultValue: Int): Int {
        val context = context
        val cr = context.contentResolver
        val uri = Uri.parse(
            SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_INT + SpContants.SEPARATOR + name
        )
        val rtn = cr.getType(uri)
        return if (rtn == null || rtn == SpContants.NULL_STRING) {
            defaultValue
        } else rtn.toInt()
    }

    fun getFloat(name: String, defaultValue: Float): Float {
        val context = context
        val cr = context.contentResolver
        val uri = Uri.parse(
            SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_FLOAT + SpContants.SEPARATOR + name
        )
        val rtn = cr.getType(uri)
        return if (rtn == null || rtn == SpContants.NULL_STRING) {
            defaultValue
        } else rtn.toFloat()
    }

    fun getBoolean(name: String, defaultValue: Boolean): Boolean {
        val context = context
        val cr = context.contentResolver
        val uri = Uri.parse(
            SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_BOOLEAN + SpContants.SEPARATOR + name
        )
        val rtn = cr.getType(uri)
        return if (rtn == null || rtn == SpContants.NULL_STRING) {
            defaultValue
        } else {
            rtn.toBoolean()
        }
    }

    fun getLong(name: String, defaultValue: Long): Long {
        val context = context
        val cr = context.contentResolver
        val uri = Uri.parse(
            SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_LONG + SpContants.SEPARATOR + name
        )
        val rtn = cr.getType(uri)
        return if (rtn == null || rtn == SpContants.NULL_STRING) {
            defaultValue
        } else rtn.toLong()
    }

    operator fun contains(name: String): Boolean {
        val context = context
        val cr = context.contentResolver
        val uri = Uri.parse(
            SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_CONTAIN + SpContants.SEPARATOR + name
        )
        val rtn = cr.getType(uri)
        return if (rtn == null || rtn == SpContants.NULL_STRING) {
            false
        } else {
            java.lang.Boolean.parseBoolean(rtn)
        }
    }

    /**
     *
     * @param name String
     * @param type String {@link SpContants#TYPE_STRING,SpContants#TYPE_INT,SpContants#TYPE_LONG,
     *                     SpContants#TYPE_FLOAT,SpContants#TYPE_DOUBLE,SpContants#TYPE_BOOLEAN}
     */
    fun remove(name: String, type: String) {
        val context = context
        val cr = context.contentResolver
        val uri = Uri.parse(
            SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + type + SpContants.SEPARATOR + name
        )
        cr.delete(uri, null, null)
    }

    fun clear() {
        val context = context
        val cr = context.contentResolver
        val uri = Uri.parse(SpContants.CONTENT_URI + SpContants.SEPARATOR + SpContants.TYPE_CLEAN)
        cr.delete(uri, null, null)
    }

    companion object {
        @Volatile
        private var mInstance: SpResolver? = null
        private val mLock = Any()

        fun getInstance(): SpResolver {
            return mInstance ?: synchronized(this) {
                mInstance ?: SpResolver().also { mInstance = it }
            }
        }
    }
}