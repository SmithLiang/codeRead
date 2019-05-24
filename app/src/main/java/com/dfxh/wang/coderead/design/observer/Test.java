package com.dfxh.wang.coderead.design.observer;

public class Test {

    Server weChatServer = new Server();

    Observer xiaowu = new User("小吴");
    Observer xiaoli = new User("小立");
    Observer xiaozhang = new User("小张");

    Test(){
        weChatServer.register(xiaowu);
        weChatServer.register(xiaoli);
        weChatServer.register(xiaozhang);

        weChatServer.post("今日消息:美国封杀华为已经开始了!");

        weChatServer.unRegister(xiaozhang);

        weChatServer.post("消息:华为只是中美贸易战的一个牺牲品");
    }


}
