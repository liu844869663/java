package com.fullmoon.study.thread;

import java.util.Random;
import java.util.function.*;

/**
 * @author jingping.liu
 * @date 2019-12-27
 * @description 函数式编程
 */
public class FunctionTest {

    private static void modifyValue1(int v, Function<Integer, Integer> function) {
        // 调用Function的apply方法，处理逻辑，返回结果
        int newValue = function.apply(v);
        System.out.println(newValue);
    }

    private static void modifyValue2(int v1, int v2, BiFunction<Integer, Integer, Integer> biFunction) {
        // 调用Function的apply方法，处理逻辑，返回结果
        int newValue = biFunction.apply(v1, v2);
        System.out.println(newValue);
    }

    /**
     * Function是一个功能性接口，有入参有且有返回结果
     */
    private static void testFunction() {
        // 1.Function
        // 传入一个值和一个实现Function中R apply(T t)方法的函数
        modifyValue1(10, (val) -> val + 20);

        // 2.BiFunction
        // 传入两个值和一个实现BiFunction中R apply(T t, U u)方法的函数
        modifyValue2(101, 102, (v1, v2) -> v2 - v1);

        // BiFunction和Function类似，都返回值，BizFunction中apply的入参有两个
    }

    /**
     * Consumer是一个消费性接口，只需要入参即可
     */
    private static void testConsumer() {
        Consumer<String> consumer = System.out::println;

        consumer.accept("test consumer");
    }

    /**
     * Supplier是一个供给性接口
     * 只有一个get方法，无参数，返回一个值
     */
    private static void testSupplier() {
        Supplier<Integer> supplier = () -> new Random().nextInt();
        System.out.println(supplier.get());
    }

    /**
     * Predicate是一个谓词性接口，起到一个判断作用
     * 只有一个方法，有入参，返回布尔类型
     */
    private static void testPredicate() {
        Predicate<Integer> predicate = t -> t == 1;
        System.out.println(predicate.test(1));
    }


    public static void main(String[] args) {

    }
}
