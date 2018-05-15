package com.ivan.microtalk.designpatterns;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {


    public Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
    }
}
