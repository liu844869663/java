package com.fullmoon.study.list;

import java.util.Hashtable;
import java.util.Map;

/**
 * 关于HashTable的学习
 *
 * HashTable继承Dictionary并实现Map接口，而HashMap继承AbstractMap，由父类实现Map
 * HashTable是线程安全的，其中方法添加了synchronized关键字,而HashMap线程不安全
 * HashTable的hash算法是直接取hashcode，而HashMap的hash算法是取hashcode后再进行位运算
 * HashTable的默认初始大小为11，扩容方式为old*2+1，而HashMap的默认初始大小为16，扩容方式为old << 1
 * HashTable的Key和Value不允许为null
 */
public class HashTableTest {
    public static void main(String[] args){
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("1","1");
        hashtable.put("2","2");
        System.out.println(0x7FFFFFFF); // Integer.MAX_VALUE
        /*for(Map.Entry<String, String> entry : hashtable.entrySet()){
            hashtable.remove(entry.getKey());
        }*/
        System.out.println(hashtable.size());
    }
}
