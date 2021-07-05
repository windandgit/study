package com.test.proxy.gpproxy;

import com.test.proxy.Person;
import com.test.proxy.dynamicproxy.Girl;
import com.test.proxy.dynamicproxy.JDKMeiPo;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

public class GPProxyTest {

    public static void main(String[] args) {
        try {
            //此处的obj是一个动态代理生成的Person接口实现类的代理对象
            Person obj = (Person) new GPMeipo().getInstance(new Girl());
            obj.findLove();

//            byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Person.class});
//            FileOutputStream os = new FileOutputStream("D:\\lianxi\\yyy.class");
//            os.write(bytes);
//            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
