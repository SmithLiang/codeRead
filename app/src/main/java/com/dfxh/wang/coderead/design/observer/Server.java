package com.dfxh.wang.coderead.design.observer;

import java.util.LinkedList;
import java.util.List;

/**
 * 被观察者
 * 举个栗子:后台服务器向订阅的用户推送消息(微信公众号)
 */
public class Server implements Observerble{

    List<Observer> users;

    public Server(){
        users = new LinkedList<>();
    }

    @Override
    public void register(Observer observer) {
        users.add(observer);
    }

    @Override
    public void unRegister(Observer observer) {
        if (!users.isEmpty()){
            users.remove(observer);
        }
    }

    @Override
    public void post(Object object) {
        for (Observer o:users) {
            o.update(object);
        }
    }





}
