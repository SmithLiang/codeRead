package com.dfxh.wang.coderead;

import android.app.Application;

import com.dfxh.wang.coderead.utils.ShareUtil;

import java.io.IOException;

public class MyApp extends Application {
//    boolean isinitApklenth=false;
    @Override
    public void onCreate() {
        super.onCreate();
        ShareUtil.get().init(this, "appdata");
//        try {
//            ShareUtil.get().set("isInitApkLen",true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (!isinitApklenth){
//
//        }
    }
    }
