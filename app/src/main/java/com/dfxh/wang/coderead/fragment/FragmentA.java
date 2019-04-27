package com.dfxh.wang.coderead.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.content.SharedPreferences;
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
import com.dfxh.wang.coderead.breakdownload.DownloadRunnable;
import com.dfxh.wang.coderead.breakdownload.TaskInfo;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

/*
* fragment的hide show()方法,只会在fragment第一次创建执行所有的生命周期方
* 法,并不会因切换而导致重新再次执行生命周期的方法
* 如要切换界面时候进行数据业务操作,用onHiddeChanged()方法进行判断
* */
public class FragmentA extends Fragment {
    View v;
    private static final String TAG  = "DownloadRunnable";

    String SHARENAME="share";
    boolean isStop=false;

    boolean isSaveLength=false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_a,null);
        Log.d(TAG, "onCreateView: A");
        setFileLength(0);
        v.findViewById(R.id.tv_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStop){
                    stopDolad();
                    isStop=false;
                }else {
                    downLoad();
                    isStop=true;
                }
            }
        });
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
        stopDolad();
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
        }else if (event.what==101){
            Log.d(TAG, "onReceiveMessage: 重置普工了");
            setFileLength(0);
        }
    }


    TaskInfo info=new TaskInfo();
    String url="https://download.alicdn.com/wireless/taobao4android/latest/702757.apk";
    String path= Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/A/";
    String filename="a.apk";
    long fileLength=0;
    DownloadRunnable mRunnable;
    void downLoad(){
        info.url=url;
        info.fileName=filename;
        info.path=path;
        info.fileContentLen=getShareLength();
        info.completeLen=getFileLength();
        mRunnable=new DownloadRunnable(info);
        new Thread(mRunnable).start();
    }
    void stopDolad(){
        if (getShareLength()==0){
            setFileLength(mRunnable.info.fileContentLen);
        }
        info.fileContentLen=getShareLength();
        info.completeLen=getFileLength();
        Log.d(TAG, "总长度是: "+info.fileContentLen);
        Log.d(TAG, "已经下载长度是: "+info.completeLen);
        mRunnable.stop();
    }

    long getFileLength(){
        File file=new File(path+filename);
        if (file.exists())
        {
            return file.length();
        }
        return 0;
    }

    void setFileLength(long fileLength){
        @SuppressLint("WrongConstant")
        SharedPreferences.Editor editor=((Context)getActivity()).getSharedPreferences(SHARENAME, Context.MODE_APPEND).edit();//file_name即为文件名
        editor.putLong("filelength", fileLength);
        editor.commit();//将数据持久化到存储介质中去
    }
    long getShareLength(){
        @SuppressLint("WrongConstant")
        SharedPreferences share=((Context)getActivity()).getSharedPreferences(SHARENAME, Context.MODE_APPEND);//file_name即为文件名
        long len=share.getLong("filelength", 0);
        return    len;
    }

}
