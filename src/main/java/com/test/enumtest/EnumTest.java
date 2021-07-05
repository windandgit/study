package com.test.enumtest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumTest {
    POSTING(1,"张三"),
    MAIN(2, "李四"),
    MAIN_ORDERED(3, "王五"),
    BACKGROUND(4, "赵六"),;

    private final Integer code;
    private final String desc;

//    private EnumTest(Integer code, String desc){
//        this.code = code;
//        this.desc = desc;
//    }

    public static String getDesc(Integer code){
        if(code == null){
            return null;
        }
        for (EnumTest e:values()) {
            if(e.getCode().equals(code)){
                return e.getDesc();
            }
        }
        return "i don`t know";
    }


}
