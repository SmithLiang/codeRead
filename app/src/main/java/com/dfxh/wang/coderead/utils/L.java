package com.dfxh.wang.coderead.utils;

import android.util.Log;

public class L {
    public static boolean isDebug =true;
    public final static String APPTAG="APPTAG";

    /**
     * 获取类名,方法名,行号
     * eg: @return [ Thread:main, at
     *     * cn.utils.MainActivity.onCreate(MainActivity.java:17)]
     */
    public static String getFunctionName(){
        StackTraceElement[] sts =Thread.currentThread().getStackTrace();
        if (sts!=null){
            for (StackTraceElement st :sts) {
                if (st.isNativeMethod()) {
                    continue;
                }
                if (st.getClassName().equals(Thread.class.getName())) {
                    continue;
                }
                if (st.getClassName().equals(L.class.getName())) {
                    continue;
                }
                return "[ Thread:" + Thread.currentThread().getName() + ", at " + st.getClassName() + "." + st.getMethodName()
                        + "(" + st.getFileName() + ":" + st.getLineNumber() + ")" + " ]";

            }
            }
        return null;
    }
    public static String getMsgFormat(String msg){
        return msg+" ;"+getFunctionName();
    }
    public static void v(String msg){
        if (isDebug)
            Log.v(APPTAG,getMsgFormat(msg));
    }
    public static  void v(String tag,String msg){
        if (isDebug)
            Log.v(tag,getMsgFormat(msg));
    }
    public static void d(String msg){
        if (isDebug)
            Log.d(APPTAG,getMsgFormat(msg));
    }
    public static  void d(String tag,String msg){
        if (isDebug)
            Log.d(tag,getMsgFormat(msg));
    }

}
