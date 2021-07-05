package com.test.proxy.gpproxy;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class GPProxy {

    private static final String ln = "\r\n";


    public static Object newProxyInstance(GPClassLoader loader,
                                          Class<?>[] interfaces,
                                          GPInvocationHandler h)throws IllegalArgumentException {

        try{
            //生成动态代理类的源代码
            String src = generateSrc(interfaces);

            //动态编译java文件输出到磁盘
            //先获取类路径
            String filePath = GPProxy.class.getResource("").getPath();
            File file = new File(filePath + "$Proxy0.java");
            FileWriter fw = new FileWriter(file);
            fw.write(src);
            fw.flush();
            fw.close();

            //把代码编译，也就是把我们动态生成的.java文件生成一个对应的.class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> iterable = standardFileManager.getJavaFileObjects(file);
            JavaCompiler.CompilationTask task = compiler.getTask(null, standardFileManager, null, null, null, iterable);
            task.call();
            standardFileManager.close();

            //我们的java文件通过上面的这段代码编译完成之后还需要加载到我们的内存中才能被我们的虚拟机使用
            Class<?> proxyClass = loader.findClass("$Proxy0");

            //获取到Class对象之后，通过反射获取到对应的构造方法(注意我们这里的构造方法中有一个InvocationHandler对象的参数)
            Constructor<?> c = proxyClass.getConstructor(GPInvocationHandler.class);
            //通过反射根据获取的构造方法获取对应的对象
            return c.newInstance(h);

//            System.out.println(src);

        }catch (Throwable e){
            e.printStackTrace();
        }
        return null;

    }

    private static String generateSrc(Class<?>[] interfaces) {
        StringBuffer sb = new StringBuffer();
        sb.append("package com.test.proxy.gpproxy;" + ln);
        sb.append("import com.test.proxy.Person;" + ln);
        sb.append("import java.lang.reflect.*;" + ln);
        sb.append("public class $Proxy0 implements " + interfaces[0].getName() + "{" + ln);
            //属性
            sb.append("GPInvocationHandler h;" + ln);
            //构造方法
            sb.append("public $Proxy0(GPInvocationHandler h){" + ln);
                sb.append("this.h = h;" + ln);
            sb.append("}" + ln);

            //循环生成接口中的方法
        for (Method m :interfaces[0].getMethods()) {
            sb.append("public " + m.getReturnType() + " " + m.getName() + "() {" + ln);
                sb.append("try{" + ln);
                    sb.append("Method m =" + interfaces[0].getName() + ".class.getMethod(\"" + m.getName() + "\",null);" + ln);
                    sb.append("this.h.invoke(this,m,null);" + ln);
                sb.append("}catch(Throwable e){" + ln);
                    sb.append("e.printStackTrace();" + ln);
                sb.append("}" + ln);
            sb.append("}" + ln);
        }

        sb.append("}");



        return sb.toString();
    }
}
