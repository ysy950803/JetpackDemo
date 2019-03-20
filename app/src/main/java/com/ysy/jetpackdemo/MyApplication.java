package com.ysy.jetpackdemo;

import android.app.Application;
import android.content.Context;

import me.weishu.reflection.Reflection;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Reflection.unseal(base);
    }
}
