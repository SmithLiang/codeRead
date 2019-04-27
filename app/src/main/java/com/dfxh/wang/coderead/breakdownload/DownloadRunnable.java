package com.dfxh.wang.coderead.breakdownload;

import android.util.Log;

import com.dfxh.wang.coderead.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadRunnable implements Runnable {
    String TAG="DownloadRunnable";
   public TaskInfo info;//下载信息bean
   private boolean isStop;//是否暂停

    public DownloadRunnable(TaskInfo info){
        this.info=info;
    }

    public void stop(){
        isStop=true;
//        if (connection!=null){
//            connection.disconnect();
//            try {
//                bufferedInputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void run() {
        HttpURLConnection connection;
        BufferedInputStream bufferedInputStream;
        RandomAccessFile accessFile;
        int len=0;
        byte[] buffer = new byte[1024*8];
        try {
            File file= new File(info.path+info.fileName);
            accessFile = new RandomAccessFile(file,"rwd");//随机读写,写入文件
            connection = (HttpURLConnection) new URL(info.url).openConnection();
            connection.setConnectTimeout(120*1000);
            connection.setReadTimeout(120*1000);
            connection.setRequestMethod("GET");
            if (info.fileContentLen==0){//如果长度为0,证明是新任务,需要从头下载
                //获取文件长度
                info.fileContentLen = Long.parseLong(connection.getHeaderField("content-length"));
                Log.d(TAG, "run: 文件总长度"+info.fileContentLen);
            }else {
                //设置属性,已完成-文件总大小
                connection.setRequestProperty("Range","bytes="+info.completeLen+"-"+info.fileContentLen);
                Log.d(TAG, "run: 已下载长度"+info.completeLen);

            }
            accessFile.seek(info.completeLen);
            connection.connect();

            bufferedInputStream= new BufferedInputStream(connection.getInputStream());

            while (!isStop && (len=bufferedInputStream.read(buffer)) !=-1 ){
                accessFile.write(buffer,0,len);
                info.completeLen=info.completeLen + len;
                Log.d(TAG, "进度 "+info.completeLen*1.0f/info.fileContentLen*100);
            }
            if (len==-1){
                Log.d(TAG, "下载完成");
                EventBus.getDefault().post(new MessageEvent(101,""));
            }else {
                Log.d(TAG, "下载停止");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "下载异常:-->"+e.toString());
        }

    }
}
