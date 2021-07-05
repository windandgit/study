package com.test.listener.demo1;

/**
 * 事件源接口
 */
public interface EvenSource {
    //增加监听器
    void addListener(EvenListener evenListener);

    //通知监听器
    void notifyListener();
}
