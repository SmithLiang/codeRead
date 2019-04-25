package com.dfxh.wang.coderead;

public class MessageEvent {
    public Object mObject;
    public int what;

    public MessageEvent(int what, Object object) {
        mObject = object;
        this.what = what;
    }
}
