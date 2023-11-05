package com.leo.sp.provider

import androidx.annotation.Keep
import androidx.annotation.StringDef

@StringDef(
    SpContants.TYPE_STRING,
    SpContants.TYPE_BOOLEAN,
    SpContants.TYPE_DOUBLE,
    SpContants.TYPE_FLOAT,
    SpContants.TYPE_LONG,
    SpContants.TYPE_INT
)
@Retention(AnnotationRetention.SOURCE)
@Keep
annotation class ContentType
