package com.ws.fast;

import android.app.Application;

import com.ws.fastlib.utils.Utils;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
