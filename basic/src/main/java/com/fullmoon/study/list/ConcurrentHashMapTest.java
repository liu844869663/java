package com.fullmoon.study.list;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 关于ConcurrentHashMap的学习
 *
 * 线程安全，对于HashTable中修改数据时锁住整个HashTable，效率低，做了优化
 * JDK7底层采用数组+链表实现（有若干个segment组成，每个segment保存若干个table）
 * JDK8取消了之前版本的segment数组，直接使用table保存数据，锁的颗粒度更小，减少并冲突的概率
 *
 * ConcurrentHashMap不允许key或者value为null
 *
 * 初始化在第一次插入数据时进行
 *
 * 利用了CAS算法保证了线程安全性，这是一种乐观策略
 * CAS：Compare and swap，比较并交换
 * 我们假设内存中的原数据V，旧的预期值A，需要修改的新值B
 * 比较 A 与 V 是否相等。（比较）
 * 如果比较相等，将 B 写入 V。否则什么都不做（交换）
 * 返回操作是否成功
 *
 */
public class ConcurrentHashMapTest {
    public static void main(String[] args){
        ConcurrentHashMap<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put(1, "1");
        concurrentHashMap.get(1);
    }
}
