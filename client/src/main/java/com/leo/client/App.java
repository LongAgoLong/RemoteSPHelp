package com.leo.client;

import android.app.Application;

import com.leo.sp.resolver.SpContants;
import com.leo.sp.resolver.SpResolver;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SpContants.initAuthority(this);
        SpResolver.getInstance().init(this);
    }
}
