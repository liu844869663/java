package com.fullmoon.study.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayTest {
    public static void main(String[] args){
        String[] strs = new String[]{"1","2","3","1"};
        // 通过Arrays工具类转换成的ArrayList集合并没有实现remove和add方法
        // 通过Arrays.asList()转换成的List无法进行remove、add操作，会抛出异常
        List<String> list1 = Arrays.asList(strs);
        List<String> list = new ArrayList<String>(list1);
        for (String str : list){
            System.out.println(str);
        }
        System.out.println(list.indexOf("1"));
        list.remove("1");
        System.out.println(list.indexOf("1"));
    }
}
