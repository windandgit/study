package com.test.proxy.gpproxy;

import com.test.proxy.Person;
import com.test.proxy.gpproxy.GPClassLoader;
import com.test.proxy.gpproxy.GPInvocationHandler;
import com.test.proxy.gpproxy.GPProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class GPMeipo implements GPInvocationHandler {

    private Person target;

    public Object getInstance(Person person) throws Exception{
        this.target = person;
        Class<?> clazz = person.getClass();
        //因为我们的MeiPo对象实现了InvocationHandler接口，所以不论是谁调用我们这里的getInstance方法，
        // 他就是一个InvocationHandler接口，所以下面这段代码中的this就是调用getInstance方法的对象本身，最后返回的就是调用getInstance方法的代理对象
        return GPProxy.newProxyInstance(new GPClassLoader(),clazz.getInterfaces(),this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        //下面这行代码的作用就是调用Person对象中的对应的方法
        Object o = method.invoke(this.target, args);
        after();
        return o;
    }

    public void before(){
        System.out.println("我是媒婆，我要给你找对象，现在已经确认你的需求，开始物色......");
    }

    public void after(){
        System.out.println("ok的话，就准备办事吧......");
    }
}
