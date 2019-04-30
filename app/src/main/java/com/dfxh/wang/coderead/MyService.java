package com.dfxh.wang.coderead;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dfxh.wang.coderead.breakdownload.singleDowload.DownRunnable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MyService extends Service {

    String url = "http://www.cagocc.cn:88/upload/apk/bdnavapp3_2.apk";

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        new Thread(new DownRunnable()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EventBus.getDefault().post(new MessageEvent(100,"service send"));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMessage(@NonNull MessageEvent event) {
        if (event.what==101){
            Log.d("wuliang"," B->@serice ");
        }
    }
}
