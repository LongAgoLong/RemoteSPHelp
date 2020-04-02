package com.leo.remotesphelp;

import android.app.Application;

import com.leo.sp.provider.SpContants;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SpContants.initAuthority(this);
    }
}
