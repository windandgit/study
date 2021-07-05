package com.test.proxy.dynamicproxy;

import com.test.proxy.Person;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

public class JDKProxyTest {

    public static void main(String[] args) {
        try {
            //此处的obj是一个动态代理生成的Person接口实现类的代理对象
            Person obj = (Person) new JDKMeiPo().getInstance(new Girl());
            obj.findLove();

            byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Person.class});
            FileOutputStream os = new FileOutputStream("D:\\lianxi\\xxx.class");
            os.write(bytes);
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
