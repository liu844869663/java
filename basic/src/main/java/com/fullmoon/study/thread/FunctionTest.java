package com.fullmoon.study.thread;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author jingping.liu
 * @date 2019-12-27
 * @description 函数式编程
 */
public class FunctionTest {

    private static void modifyValue1(int v, Function<Integer, Integer> function) {
        int newValue = function.apply(v);
        System.out.println(newValue);
    }

    private static void modifyValue2(int v1, int v2, BiFunction<Integer, Integer, Integer> biFunction) {
        int newValue = biFunction.apply(v1, v2);
        System.out.println(newValue);
    }

    public static void main(String[] args) {
        // 1.Function
        // 传入一个值和一个实现Function中R apply(T t)方法的函数
        modifyValue1(10, (val) -> val + 20);

        // 2.BiFunction
        // 传入两个值和一个实现BiFunction中R apply(T t, U u)方法的函数
        modifyValue2(101, 102, (v1, v2) -> v2 - v1);

        // BiFunction和Function类似，BizFunction中apply的入参有两个
    }
}
