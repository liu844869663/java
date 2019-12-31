package com.fullmoon.study.list;

import java.util.TreeMap;

/**
 * 关于TreeMap的学习
 *
 * TreeMap是一个有序的key-value集合，底层是一个红黑树
 * 时间复杂程度：O（log n）
 */
public class TreeMapTest {
    public static void main(String[] args){
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "1");
        treeMap.hashCode();
    }

}
