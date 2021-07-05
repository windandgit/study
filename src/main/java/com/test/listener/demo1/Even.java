package com.test.listener.demo1;

import java.io.Serializable;
import java.util.Date;

/**
 * 事件对象接口：里面封装了事件的所有信息的属性
 */
public interface Even extends Serializable {
    Object source();
    Date when();
    String message();

    /**
     * 事件的回调方法
     */
    void callback();

}
