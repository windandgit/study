package com.test.proxy.dynamicproxy;

import com.test.proxy.Person;

public class Girl implements Person {
    @Override
    public void findLove() {
        System.out.println("身高180以上");
        System.out.println("高富帅");
        System.out.println("有6块腹肌");
    }
}
