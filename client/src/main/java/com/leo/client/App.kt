package com.leo.client

import android.app.Application
import com.leo.sp.resolver.SpContants
import com.leo.sp.resolver.SpResolver

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SpContants.initAuthority(this)
        SpResolver.getInstance().init(this)
    }
}
