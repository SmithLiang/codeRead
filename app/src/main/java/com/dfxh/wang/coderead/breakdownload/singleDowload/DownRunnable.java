package com.dfxh.wang.coderead.breakdownload.singleDowload;
import android.os.Environment;
import android.util.Log;

import com.dfxh.wang.coderead.MessageEvent;
import com.dfxh.wang.coderead.utils.ShareUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownRunnable implements Runnable {
    /**
     * 1.拿到下载url进行connection连接
     * 2.新建file本地下载apk的包
     * 3.判断已经下载文件大小
     * 4.通过randomaceefile断点下载
     * 5.通过sharePreference存入文件总长度
     * 6.如果已经下载了部分,则进行con到那上面
     *
     * 逻辑
     * 1.第一次下载时候,将totalLen总长度存入sharesPreference中
     * 2.再次下载,从shared中取出总长度,进行比对
     * 3.下载完成了以后,将shared总长度重置为0
     * 4.考虑一下情况
     * 卸载了apk shared值为0,会出错
     * 安装完成以后,shared值0,完成的安装包complen不为0,会出错
     */
    private  String appfilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/A/a.apk";
   // String url="https://download.alicdn.com/wireless/taobao4android/latest/702757.apk";
    String url="http://www.cagocc.cn:88/upload/apk/bdnavapp3_2.apk";//服务器上面的
    private  long compleLen;//已完成
    private long totalLen;//总长度
    String TAG="DownRunnable";
    private File file;
    @Override
    public void run() {
        HttpURLConnection connection;
        BufferedInputStream bufferedInputStream;
        RandomAccessFile accessFile;
        int len;
        byte[] buffer = new byte[1024*8];
        try {
            file= new File(appfilePath);
            compleLen = file.length();
            totalLen = ShareUtil.get().getLong("totalLen");
            Log.d(TAG, "run: 已经下载"+compleLen);
            Log.d(TAG, "run: 总共大小"+totalLen);
            if (compleLen>totalLen){
                //证明软件卸载了,shared值为0,下载文件还在,或者已完成了下载,没有删除原来包
                //解决方法:删除原来安装包重新下载 ,新建一个文件下载
                if (file.exists()){
                    file.delete();
                    //重新创建文件
                    Log.d(TAG, "run: 执行到这方法了");
                    file= new File(appfilePath);
                }
            }
                accessFile = new RandomAccessFile(file,"rwd");//随机读写,写入文件
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setConnectTimeout(10*1000);
                connection.setReadTimeout(10*1000);
                connection.setRequestMethod("GET");


                if (compleLen==0){//如果长度为0,证明是新任务,需要从头下载
                    //获取文件长度
                    totalLen = Long.parseLong(connection.getHeaderField("content-length"));
                    ShareUtil.get().set("totalLen",totalLen);
                }else {
                    //设置属性,已完成-文件总大小
                    connection.setRequestProperty("Range","bytes="+compleLen+"-"+totalLen);
                    Log.d(TAG, "run: 总长度长度"+totalLen);
                    Log.d(TAG, "run: 已下载长度"+compleLen);
                }
                accessFile.seek(compleLen);
                connection.connect();

                bufferedInputStream= new BufferedInputStream(connection.getInputStream());
                while ( (len=bufferedInputStream.read(buffer)) !=-1 ){
                    accessFile.write(buffer,0,len);
                    compleLen=compleLen + len;
                    Log.d(TAG, "进度 "+compleLen*1.0f/totalLen*100);
                }
                if (len==-1){
                    Log.d(TAG, "下载完成");
                    ShareUtil.get().set("totalLen",0l);//完成后把长度重置
                    EventBus.getDefault().post(new MessageEvent(101,""));
                }else {
                    Log.d(TAG, "下载停止");
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.d(TAG, "下载异常:-->"+e.toString());
                e.printStackTrace();
            }

           
    }
}
