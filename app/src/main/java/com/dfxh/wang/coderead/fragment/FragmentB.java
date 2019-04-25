package com.dfxh.wang.coderead.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dfxh.wang.coderead.MessageEvent;
import com.dfxh.wang.coderead.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentB extends Fragment {
    View v;
    private static final String TAG  = "Fragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_b,null);
        Log.d(TAG, "onCreateView:B ");
        return v;
    }
    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){//加上判断
            EventBus.getDefault().register(this);
        }
        Log.d(TAG, "onStart: b");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: b");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: b");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Log.d(TAG, "onHiddenChanged: B隐藏了");
        }else {
            Log.d(TAG, "onHiddenChanged: B显示了");
            EventBus.getDefault().post(new MessageEvent(101,"消息"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){//加上判断
            EventBus.getDefault().unregister(this);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMessage(@NonNull MessageEvent event) {
        if (event.what==101){
            Log.d("wuliang","自己的@@@@ B->@serice ");
        }
    }
}
