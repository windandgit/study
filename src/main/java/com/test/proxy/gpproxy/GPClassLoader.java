package com.test.proxy.gpproxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class GPClassLoader extends ClassLoader {

    private File classPathFile;
    public GPClassLoader(){
        String classPath = GPClassLoader.class.getResource("").getPath();
        this.classPathFile = new File(classPath);

    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = GPClassLoader.class.getPackage().getName() + "." + name;
        if(classPathFile != null){
            //把我们动态生成的.java转化为.class文件
            File classFile = new File(classPathFile,name.replace("\\.","/") + ".class");
            //把我们获取到的.class文件转化为字节码文件并加载到内存中
            if(classFile.exists()){
                FileInputStream in = null;
                ByteArrayOutputStream out = null;
                try {
                    in = new FileInputStream(classFile);
                    out = new ByteArrayOutputStream();
                    byte[] buff = new byte[1024];
                    int len;
                    while((len = in.read(buff)) !=-1){
                        out.write(buff,0,len);
                    }
                    //下面的defineClass方法是一个原生(native)方法，把我们的字节码文件转化为一个Class对象
                    return defineClass(className,out.toByteArray(),0,out.size());

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }

        return null;
    }
}
