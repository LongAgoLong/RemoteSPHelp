package com.leo.sp.provider.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.EmptyCoroutineContext

val Context.dataStorePf by preferencesDataStore(
    name = "remote_datastore"
)

val Context.scope: CoroutineScope
    get() = CoroutineScope(EmptyCoroutineContext)