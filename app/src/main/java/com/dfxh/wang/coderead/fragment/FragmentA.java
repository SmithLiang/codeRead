package com.dfxh.wang.coderead.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dfxh.wang.coderead.MessageEvent;
import com.dfxh.wang.coderead.MyService;
import com.dfxh.wang.coderead.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/*
* fragment的hide show()方法,只会在fragment第一次创建执行所有的生命周期方
* 法,并不会因切换而导致重新再次执行生命周期的方法
* 如要切换界面时候进行数据业务操作,用onHiddeChanged()方法进行判断
* */
public class FragmentA extends Fragment {
    View v;
    private static final String TAG  = "Fragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_a,null);
        Log.d(TAG, "onCreateView: A");
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){//加上判断
            EventBus.getDefault().register(this);
        }
        getActivity().startService(new Intent(getContext(), MyService.class));
        Log.d(TAG, "onStart: A");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: A");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: A");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){//加上判断
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            Log.d(TAG, "onHiddenChanged: A隐藏了 ");
        }else {
            Log.d(TAG, "onHiddenChanged: A显示了");
        }
    }

    /*
     * 接收到EventBus发布的消息事
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMessage(@NonNull MessageEvent event) {
        if (event.what==100){
            Log.d("wuliang","fragment");
        }
    }
}
