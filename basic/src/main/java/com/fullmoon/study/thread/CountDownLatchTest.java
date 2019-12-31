package com.fullmoon.study.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch是通过一个计数器来实现的，计数器的初始值为线程的数量。
 * 1. 每当一个线程完成了自己的任务后，可以调用其countDown()方法，使计数器的值减1
 * 2. 当计数器为0时，表示所有的线程都完成任务，然后唤醒在闭锁上等待的线程，就可以恢复执行任务
 * 注：这是一个一次性操作-计数器无法重置，如果你需要一个重置的版本数，考虑使用CyclicBarrier
 *
 * 其原理是使用AQS底层框架，该框架用于构建锁和同步容器
 * AQS的核心原理之一就是利用CAS更新同步状态，CAS是一种解决并发问题的思想(原子操作)，Compare And Swap
 *
 * @author jingping.liu
 * @date 2019-12-12
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        // 设置计数器的初始值为2
        CountDownLatch latch = new CountDownLatch(2);
        System.out.println("主线程开始了");
        exec(latch);
        exec(latch);
        System.out.println("等待两个线程执行完毕");
        try {
            // 开启latch，当其计数器为0时，才继续往下执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("两个子线程都执行完毕，继续执行主线程");
    }

    private static void exec(CountDownLatch latch) {
        // 创建单线程的线程池
        ExecutorService service = Executors.newSingleThreadExecutor();
        // 线程池中执行下述任务
        service.execute(() -> {
            try {
                System.out.println("子线程：" + Thread.currentThread().getName() + "准备就绪");
                Thread.sleep(5000);
                System.out.println("子线程：" + Thread.currentThread().getName() + "执行完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*
             * 计数器减1，直到计数器为0，则释放等待的线程
             * 如果当前计数器大于0，则递减
             * 如果新的计数器为0，则重新启用等待的线程，达到线程调度的目的
             * 如果当前计数器为0。则不做处理
             */
            latch.countDown();
        });
        // 线程池的状态设置为SHUTDOWN，不能再往线程池中添加新任务
        // 此时线程池不会立即退出，在线程池中的所有任务执行完成后，线程池的状态才会变成STOP
        service.shutdown();
    }
}
