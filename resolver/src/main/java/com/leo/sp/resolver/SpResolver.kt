package com.leo.sp.resolver

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

    fun putBoolean(name: String, t: Boolean) {
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

    fun putString(name: String, t: String) {
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

    fun putInt(name: String, t: Int) {
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

    fun putLong(name: String, t: Long) {
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

    fun putFloat(name: String, t: Float) {
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

    fun putDouble(name: String, t: Double) {
        val context = context
        val cr = context.contentResolver
        synchronized(mLock) {
            val uri = Uri.parse(
                SpContants.CONTENT_URI + SpContants.SEPARATOR
                        + SpContants.TYPE_DOUBLE + SpContants.SEPARATOR + name
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
        return if (isInvalidResult(rtn)) {
            defaultValue
        } else rtn!!
    }

    fun getInt(name: String, defaultValue: Int): Int {
        val context = context
        val cr = context.contentResolver
        val uri = Uri.parse(
            SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_INT + SpContants.SEPARATOR + name
        )
        val rtn = cr.getType(uri)
        return if (isInvalidResult(rtn)) {
            defaultValue
        } else rtn!!.toInt()
    }

    fun getFloat(name: String, defaultValue: Float): Float {
        val context = context
        val cr = context.contentResolver
        val uri = Uri.parse(
            SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_FLOAT + SpContants.SEPARATOR + name
        )
        val rtn = cr.getType(uri)
        return if (isInvalidResult(rtn)) {
            defaultValue
        } else rtn!!.toFloat()
    }

    fun getDouble(name: String, defaultValue: Double): Double {
        val context = context
        val cr = context.contentResolver
        val uri = Uri.parse(
            SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_DOUBLE + SpContants.SEPARATOR + name
        )
        val rtn = cr.getType(uri)
        return if (isInvalidResult(rtn)) {
            defaultValue
        } else rtn!!.toDouble()
    }

    fun getBoolean(name: String, defaultValue: Boolean): Boolean {
        val context = context
        val cr = context.contentResolver
        val uri = Uri.parse(
            SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_BOOLEAN + SpContants.SEPARATOR + name
        )
        val rtn = cr.getType(uri)
        return if (isInvalidResult(rtn)) {
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
        return if (isInvalidResult(rtn)) {
            defaultValue
        } else rtn!!.toLong()
    }

    operator fun contains(name: String): Boolean {
        val context = context
        val cr = context.contentResolver
        val uri = Uri.parse(
            SpContants.CONTENT_URI + SpContants.SEPARATOR
                    + SpContants.TYPE_CONTAIN + SpContants.SEPARATOR + name
        )
        val rtn = cr.getType(uri)
        return if (isInvalidResult(rtn)) {
            false
        } else {
            rtn.toBoolean()
        }
    }

    /**
     *
     * @param name String
     * @param type String {@link SpContants#TYPE_STRING,SpContants#TYPE_INT,SpContants#TYPE_LONG,
     *                     SpContants#TYPE_FLOAT,SpContants#TYPE_DOUBLE,SpContants#TYPE_BOOLEAN}
     */
    fun remove(name: String, @ContentType type: String) {
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

    private fun isInvalidResult(result: String?): Boolean {
        return result == null || result == SpContants.NULL_STRING;
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