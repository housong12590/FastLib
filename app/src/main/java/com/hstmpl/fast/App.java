package com.hstmpl.fast;

import android.app.Application;

import com.hstmpl.util.Utils;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
