package com.dfxh.wang.coderead;

import android.app.Application;

import com.dfxh.wang.coderead.utils.ShareUtil;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ShareUtil.get().init(this,"appdata");
    }
}
