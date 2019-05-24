package com.dfxh.wang.coderead.design.observer;

/**
 * 定义:
 * 对象之间存在一对多的依赖,当一发生改变的时候,多会收到通知并且更新
 * 举个栗子: eventbus相当于被观察者
 * 被观察者接口
 * 声明了添加,移除,通知观察者的方法
 */
public interface Observerble {
    void register(Observer observer);
    void unRegister(Observer observer);
    void post(Object object);
}
