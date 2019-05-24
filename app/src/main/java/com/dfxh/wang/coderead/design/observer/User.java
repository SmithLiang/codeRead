package com.dfxh.wang.coderead.design.observer;

import android.util.Log;

/**
 * 观察者,实现观察者接口
 */
public class User implements Observer{
    String TAG = getClass().getName();
    String name;

    public User(String name){
        this.name = name;
    }

    @Override
    public void update(Object object) {
        Log.d(TAG, name+"收到了后台推送的消息:--->"+(String)object);
    }


}
