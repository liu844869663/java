package com.fullmoon.study.list;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * 关于LinkedHashMap的学习
 *
 * LinkedHashMap继承HashMap，特性都差不多，通过维护一条双向链表，保证内部数据的顺序
 * 顺序分为：按插入时候的顺序（默认），按获取数据时的顺序
 *
 * 如何保证的顺序性？
 * 保存的节点Entry<K,V>通过继承HashMap的Node<K,V>，添加Entry<K,V> before, after
 * 在元素添加的时候调用HashMap的put方法，然后再元素添加到双向链表中
 * before表示元素的上一个元素，after表示元素的后一个元素
 * 遍历的时候通过链表的顺序一个一个遍历
 */
public class LinkedHashMapTest {
    public static void main(String[]  args){
        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<Integer, String>();
        linkedHashMap.put(1, "1");
        linkedHashMap.put(null, "1");
        linkedHashMap.put(2, null);
        linkedHashMap.get(1);
        System.out.println(linkedHashMap.getOrDefault(2,"1"));
        Set<String> set = new HashSet<>();
    }
}
