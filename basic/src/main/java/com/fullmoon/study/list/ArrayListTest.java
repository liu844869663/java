package com.fullmoon.study.list;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;

public class ArrayListTest {
    public static void main(String[] args){
        ArrayList<String> arrayList = new ArrayList<>();
        LinkedList<String> linkedList = new LinkedList<>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        /*Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }*/
        for(String item : arrayList){
            arrayList.remove(item);
        }

    }
}
