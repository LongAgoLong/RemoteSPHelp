package com.leo.sp.provider.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object SpDataStoreImpl {

    /**
     * 支持的存储类型
     */
    private val supportClassList = mutableListOf(
        Int::class,
        String::class,
        Double::class,
        Float::class,
        Boolean::class,
        Long::class,
    )

    /**
     * 异步保存
     * @param context Context
     * @param key String
     * @param value Any
     */
    fun <T> put(context: Context, key: String, value: Any, type: T) {
        context.scope.launch {
            val keyValue = getKey(key, type)
            context.dataStorePf.edit { map ->
                map[keyValue] = value as T
            }
        }
    }

    /**
     * 同步保存
     * @param context Context
     * @param key String
     * @param value Any
     */
    fun <T> putSync(context: Context, key: String, value: Any, type: T) {
        runBlocking {
            val keyValue = getKey(key, type)
            context.dataStorePf.edit { map ->
                map[keyValue] = value as T
            }
        }
    }

    /**
     * 移除
     * @param context Context
     * @param key String
     * @param type Class<T>
     */
    fun <T> remove(context: Context, key: String, type: T) {
        context.scope.launch {
            val keyValue = getKey(key, type)
            context.dataStorePf.edit {
                it.remove(keyValue)
            }
        }
    }

    @Deprecated(
        message = "不推荐方法|耗时",
        replaceWith = ReplaceWith("SpDataStoreImpl#remove(Context, String, KClass<Any>)"),
        level = DeprecationLevel.WARNING
    )
    fun remove(context: Context, key: String) {
        context.scope.launch {
            supportClassList.forEach { type ->
                val keyValue = getKey(key, type)
                context.dataStorePf.edit {
                    it.remove(keyValue)
                }
            }
        }
    }

    /**
     * 同步移除
     * @param context Context
     * @param key String
     * @param type KClass<Any>
     */
    fun <T> removeSync(context: Context, key: String, type: T) {
        runBlocking {
            val keyValue = getKey(key, type)
            context.dataStorePf.edit {
                it.remove(keyValue)
            }
        }
    }

    /**
     * 是否包含
     * @param context Context
     * @param key String
     * @param type KClass<Any>
     * @return Boolean
     */
    fun <T> containsSync(context: Context, key: String, type: T): Boolean {
        val async = context.scope.async(Dispatchers.IO) {
            var isContains = false
            val keyValue = getKey(key, type)
            context.dataStorePf.edit {
                isContains = it.contains(keyValue)
            }
            isContains
        }
        var result: Boolean
        runBlocking {
            result = async.await()
        }
        return result
    }

    @Deprecated(
        message = "不推荐方法|耗时",
        replaceWith = ReplaceWith("SpDataStoreImpl#containsSync(Context, String, KClass<Any>)"),
        level = DeprecationLevel.WARNING
    )
    fun containsSync(context: Context, key: String): Boolean {
        val deferredResult = context.scope.async(Dispatchers.IO) {
            var isContains = false
            supportClassList.forEach { type ->
                val keyValue = getKey(key, type)
                context.dataStorePf.edit {
                    isContains = it.contains(keyValue)
                }
                if (isContains) {
                    return@forEach
                }
            }
            isContains
        }
        var result: Boolean
        runBlocking {
            result = deferredResult.await()
        }
        return result
    }

    /**
     * 同步获取
     * @param context Context
     * @param key String
     * @param type KClass<Any>
     * @return T?
     */
    fun <T> getSync(context: Context, key: String, type: T): Any? {
        val async = context.scope.async(Dispatchers.IO) {
            get(context, key, type).first()
        }
        var result: Any?
        runBlocking {
            result = async.await()
        }
        return result
    }

    fun <T> get(context: Context, key: String, type: T): Flow<Any?> {
        val keyValue = getKey(key, type)
        val flow = context.dataStorePf.data.map { map ->
            map[keyValue]
        }
        return flow
    }

    /**
     * 异步清空
     * @param context Context
     */
    fun clear(context: Context) {
        context.scope.launch {
            context.dataStorePf.edit {
                it.clear()
            }
        }
    }

    /**
     * 同步清空
     * @param context Context
     */
    fun clearSync(context: Context) {
        runBlocking {
            context.dataStorePf.edit {
                it.clear()
            }
        }
    }

    private fun <T> getKey(key: String, type: T): Preferences.Key<T> {
        val keyValue: Preferences.Key<T>?
        when (type) {
            Int::class -> {
                keyValue = intPreferencesKey(key) as Preferences.Key<T>
            }
            String::class -> {
                keyValue = stringPreferencesKey(key) as Preferences.Key<T>
            }
            Double::class -> {
                keyValue = doublePreferencesKey(key) as Preferences.Key<T>
            }
            Float::class -> {
                keyValue = floatPreferencesKey(key) as Preferences.Key<T>
            }
            Boolean::class -> {
                keyValue = booleanPreferencesKey(key) as Preferences.Key<T>
            }
            Long::class -> {
                keyValue = longPreferencesKey(key) as Preferences.Key<T>
            }
            else -> {
                throw RuntimeException("key type is not support,you need check you key type!! -> " + type)
            }
        }
        return keyValue
    }
}
