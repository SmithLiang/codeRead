package com.dfxh.wang.coderead.design.observer;

/**
 * 定义观察者
    声明了被观察者调用post的时候,观察者的update会被调用
 */
public interface Observer {
    void update(Object object);
}
