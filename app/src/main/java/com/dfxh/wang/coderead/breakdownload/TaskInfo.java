package com.dfxh.wang.coderead.breakdownload;

public class TaskInfo
{
    public String fileName;//文件名
    public String path;//文件路径
    public String url;//链接
    public long fileContentLen;//文件总长度
    //long double非线程安全的,必须要用volatile修饰
    public volatile long completeLen;//已完成长度


}
