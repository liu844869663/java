package com.fullmoon.study.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CyclicBarrier 是一种同步机制，它可以使得一组线程在同一个障碍点进行等待，在涉及到固定个数的线程组且有时必须互相等待时，则可以使用 CyclicBarrier 来进行实现
 * CyclicBarrier前缀有cyclic,是因为在线程释放后,CyclicBarriers可以通过重置 计数器从而重新使用
 *
 * CyclicBarrier支持一个可选的Runnable命令，该命令在最后一个线程到达后，但在任何线程被释放之前，这一命令在barrier只会被执行一次,
 * 且由最后到达的线程完成。这种屏障行为对于在任何一方继续之前更新共享状态都很有用
 *
 * 在同步失败时,CyclicBarrier使用了一种all-or-none破损模型:如果一个线程因为中断(or执行过程的失败,超时等)过早的离开了barrier点,
 * 那么等待在barrier点的其他所有线程也会在同一时间因为BrokenBarrierException或者InterruptedException异常而离开barrier
 *
 * 内存一致性影响:线程在调用await()方法之前的行为要优先于barrier action中的任何行为,barrier action成功返回这一行为要优先于所有其他等待线程await()后的行为
 *
 * 注意：CyclicBarrier使用独占锁来执行await方法，并发性可能不是很高
 *
 * CountDownLatch与CyclicBarrier的比较
 * 内部都是通过一个计数器来维护
 * 当计数器为0时，CountDownLatch释放等待线程，CyclicBarrier执行指定任务(如果有)，释放其他等待的线程
 * CyclicBarrier的计数器可重复利用，而CountDownLatch不可
 *
 * @author jingping.liu
 * @date 2019-09-29
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        int threadNum = 5;
        CyclicBarrierTest test = new CyclicBarrierTest();
        test.testCyclicBarrier(threadNum);
        //test.testCountDownLatch(threadNum);
    }

    private void testCyclicBarrier(int threadNums) {
        // 创建一个任务，在栅栏解除时执行
        Runnable taskTemp = new Runnable() {
            private int iCounter;

            @Override
            public void run() {
                iCounter++;
                System.out.println(System.currentTimeMillis() + " [" + Thread.currentThread().getName() + "] iCounter = " + iCounter);
            }
        };
        startNThreadsByBarrier(threadNums, taskTemp);
    }

    private void startNThreadsByBarrier(int threadNums, Runnable finishTask) {
        // 设置栅栏解除时的动作，比如初始化某些值，最后一个线程到达栅栏时执行
        CyclicBarrier barrier = new CyclicBarrier(threadNums, finishTask);
        // 启动 n 个线程，与栅栏阀值一致，即当线程准备数达到要求时，栅栏刚好开启，从而达到统一控制效果
        for (int i = 0; i < threadNums; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(new CounterTask(barrier)).start();
        }
        System.out.println(Thread.currentThread().getName() + " out over...");
    }

    class CounterTask implements Runnable {
        // 传入栅栏，一般考虑更优雅方式
        private CyclicBarrier barrier;

        CounterTask(final CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " - " + System.currentTimeMillis() + " is ready...");
            try {
                System.out.println("now number waiting:" + (barrier.getNumberWaiting() + 1)); // 还没执行await()方法,这里先加1
                /*
                 * 告诉 CyclicBarrier 当前线程已经到达栅栏处，在此等待被阻塞，等待CyclicBarrier中parties个线程到达栅栏处
                 *
                 * 如果当前线程并不是到达的最后一个线程,则它被禁用线程调度目的，并且处于休眠状态，直到发生以下事件之一：
                 * 1.最后一个线程到达
                 * 2.其他线程中断了当前线程
                 * 3.其它线程中断了其它等待的线程
                 * 4.在barrier上面等待的线程发生超时
                 * 5.其它线程调用了barrier上面的reset方法
                 *
                 * 如果当前线程在进入这一方法时,中断状态位被标记或者在等待过程中被中断,则会抛出中断异常InterruptedException,且当前线程的中断状态被清除
                 * 会抛出BrokenBarrierException异常的情况有：
                 * 1.当其它线程在等待时,如果barrier被reset
                 * 2.当调用await()方法时barrier发生了broken
                 *
                 * 任意等待线程发生了中断异常时,其它等待线程都会抛出BrokenBarrierException,且barrier的状态会变为broken
                 *
                 * 如果当前线程是最后一个到达barrier的线程,且构造函数中的barrier action非null,则在其它线程可以继续执行前,当前线程会执行barrier action
                 * 如果在barrier action的执行过程中发生了异常,则该异常会对当前线程产生影响,且barrier的会处于broken状态
                 *
                 */
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " - " + System.currentTimeMillis() + " started...");
        }
    }


    private void testCountDownLatch(int threadNum) {
        Runnable taskTemp = new Runnable() {
            // 原子操作类
            private AtomicInteger iCounter = new AtomicInteger();

            @Override
            public void run() {
                for (int i = 0; i < threadNum; i++) {
                    iCounter.incrementAndGet();
                    System.out.println(System.nanoTime() + " [" + Thread.currentThread().getName() + "] iCounter=" + iCounter);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        startTaskAllInOnce(threadNum, taskTemp);
    }


    private void startTaskAllInOnce(int threadNum, final Runnable task) {
        final CountDownLatch startGet = new CountDownLatch(1);
        final CountDownLatch endGet = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            new Thread(() -> {
                try {
                    // 使线程在此等待，当开始门打开时一起涌入门中
                    startGet.await();
                    try {
                        task.run();
                    } finally {
                        // 将endGet的计数器减1，减到0时，就可以开启结束门了
                        endGet.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        long startTime = System.nanoTime();
        System.out.println(startTime + " [" + Thread.currentThread() + "] All thread is ready, concurrent going...");
        // 上面 threadNum 个线程(每个线程循环多少次输出)已经全部准备就绪
        // startGet的计数器为1，下面即将该计数器至为0，则上面的 threadNum 个线程则开始执行 task 任务
        startGet.countDown();
        try {
            // 开启结束门，当 endGet 的计数器为0时，则打开结束门，然后继续执行
            endGet.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        System.out.println(endTime + " [" + Thread.currentThread() + "] All thread is completed.");
    }
}
