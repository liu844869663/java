package com.fullmoon.study.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author jingping.liu
 * @date 2019-12-26
 * @description TODO
 */
public class FutureTest {
    public static void main(String[] args) {
        CompletableFuture<Integer> futurePrice = getPriceAsync();
        // 不被阻塞
        System.out.println(1);
        // 任务完成执行回调函数
        futurePrice.whenComplete((i, throwable) -> {
            System.out.println(i);
        });
        System.out.println(2);
    }

    static CompletableFuture<Integer> getPriceAsync(){
        CompletableFuture<Integer> futurePrice = new CompletableFuture<>();
        new Thread(() ->{
            try {
                TimeUnit.MILLISECONDS.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            futurePrice.complete(98);
        }).start();
        return futurePrice;
    }
}
