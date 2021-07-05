package com.test.enumtest;

public class TestEnum {

    public static void main(String[] args) {
        System.out.println(EnumTest.getDesc(1) );
        System.out.println(EnumTest.getDesc(2) );
        System.out.println(EnumTest.getDesc(3) );
        System.out.println(EnumTest.getDesc(10) );

        System.out.println("-----------------");
        EnumTest posting = EnumTest.valueOf("POSTING");
        System.out.println("====================");
        System.out.println(posting);
        System.out.println(posting.getCode());
        System.out.println(posting.getDesc());

        System.out.println("**************");
        String desc = EnumTest.getDesc(0);
        System.out.println("获取到的值为：" + desc.toString());
    }
}
