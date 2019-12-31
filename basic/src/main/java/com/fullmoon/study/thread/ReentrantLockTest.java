package com.fullmoon.study.thread;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * JDK中独占锁的实现：关键字synchronized，ReentrantLock
 *
 * @author jingping.liu
 * @date 2019-12-13
 */
public class ReentrantLockTest {

    public static void main(String[] args) {
        ReentrantLockTest test = new ReentrantLockTest();
        int i = 2;
        switch (i) {
            case 1:
                test.test1();
                break;
            case 2:
                test.test2();
                break;
            case 3:
                test.test3();
                break;
            case 4:
                test.test4();
                break;
            case 5:
                test.test5();
                break;
            case 6:
                test.test6();
                break;
        }
    }

    /**
     * ReentrantLock 是可重入锁，当一个线程获取锁时，该线程还可以接着重复获取多次
     * ReentrantLock 和 synchronized 关键字都是独占锁，只允许线程互斥的访问临界区
     * 1.synchronized 加锁解锁的过程是隐式的，用户不用手动操作，优点是比较简单，但显得不够灵活
     * 2.ReentrantLock 需要手动加锁解锁，且解锁操作尽量保证进行以释放锁，否则导致其他线程无法获取锁
     */
    private void test1() {
        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < 3; i++) {
            // 获取锁，不可被中断
            reentrantLock.lock();
        }
        for (int i = 0; i < 3; i++) {
            try {
                System.out.println("做点什么...");
            } finally {
                reentrantLock.unlock();
            }
        }
        new Thread(() -> {
            // 如果上面线程没有释放锁，则该线程是无法获取到该锁，将无法继续进行
            reentrantLock.lock();
            System.out.println("ReentrantLock.");
            reentrantLock.unlock();
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                // 可重入，不用人工去释放锁
                synchronized (this) {
                    System.out.println("synchronized.");
                }
            }
        }).start();
    }

    /**
     * ReentrantLock分为公平锁和非公平锁
     * 公平和非公平锁的队列都基于锁内部维护的一个双向链表，表结点Node的值就是每一个请求当前锁的线程
     * 公平锁：获取锁的顺序按照线程达到的顺序，公平锁的实现机理在于每次有线程来抢占锁的时候，都会检查一遍有没有等待队列(同步队列)，有则唤醒队列中第一个线程
     * 非公平锁：线程不必加入等待队列就可以获得锁，哪怕同步队列中已有线程再等待，进行插队也有机会获取锁，太不公平了
     */
    private void test2() {
        for (int i = 0; i < 5; i++) {
            new Thread(new Test2Thread(i)).start();
        }
    }

    // 公平锁
    private static ReentrantLock test2Lock = new ReentrantLock(true);

    class Test2Thread implements Runnable {
        private int id;

        Test2Thread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 2; i++) {
                test2Lock.lock();
                System.out.println("获得锁的线程：" + id);
                test2Lock.unlock();
            }
        }
    }

    private static Lock test3Lock1 = new ReentrantLock();
    private static Lock test3Lock2 = new ReentrantLock();

    /**
     * 当使用synchronized实现锁时,阻塞在锁上的线程除非获得锁否则将一直等待下去，也就是说这种无限等待获取锁的行为无法被中断
     * ReentrantLock提供了一个可以响应中断获取锁的方法
     */
    private void test3() {
        Thread thread1 = new Thread(new Test3Thread(test3Lock1, test3Lock2));
        Thread thread2 = new Thread(new Test3Thread(test3Lock2, test3Lock1));
        // 两个线程互相等待对方释放锁，进入死锁状态
        thread1.start();
        thread2.start();
        // 线程1中断，锁则被中断，线程2则可正常运行
        thread1.interrupt();

    }

    class Test3Thread implements Runnable {
        private Lock firstLock;
        private Lock secondLock;

        Test3Thread(Lock firstLock, Lock secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }

        @Override
        public void run() {
            try {
                // 可响应中断获取锁
                firstLock.lockInterruptibly();
                TimeUnit.MILLISECONDS.sleep(10);//更好的触发死锁
                secondLock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放锁
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(Thread.currentThread().getName() + "正常结束!");
            }
        }
    }

    private static Lock test4Lock1 = new ReentrantLock();
    private static Lock test4Lock2 = new ReentrantLock();

    /**
     * ReentrantLock提供了获取锁限时等待的方法tryLock()
     */
    private void test4() {
        Thread thread1 = new Thread(new Test4Thread(test4Lock1, test4Lock2));
        Thread thread2 = new Thread(new Test4Thread(test4Lock2, test4Lock1));
        thread1.start();
        thread2.start();
    }

    class Test4Thread implements Runnable {
        private Lock firstLock;
        private Lock secondLock;

        Test4Thread(Lock firstLock, Lock secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }

        @Override
        public void run() {
            try {
                // 获取锁时限时等待，true 成功 false 失败
                // 可传入时间参数无参表示立即返回获取锁结果
                while (!firstLock.tryLock()) {
                    TimeUnit.MILLISECONDS.sleep(10);
                }
                while (!secondLock.tryLock()) {
                    firstLock.unlock();
                    TimeUnit.MILLISECONDS.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放锁
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(Thread.currentThread().getName() + "正常结束!");
            }
        }
    }

    private static Lock test5Lock1 = new ReentrantLock();
    private static Condition condition = test5Lock1.newCondition();

    /**
     * ReentrantLock可结合Condition实现等待通知机制
     * 调用Condition的await()方法将当前线程进入等待状态
     * 调用Condition的signal()方法将唤醒一个线程
     */
    private void test5() {
        test5Lock1.lock();
        new Thread(new OtherThread()).start();
        new Thread(new SignalThread()).start();
        System.out.println("主线程等待通知");
        try {
            // 当前线程进入等待
            condition.await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            test5Lock1.unlock();
        }
        System.out.println("主线程恢复运行");
    }

    static class SignalThread implements Runnable {

        @Override
        public void run() {
            test5Lock1.lock();
            try {
                TimeUnit.MILLISECONDS.sleep(5000);
                // 唤醒线程
                condition.signal();
                System.out.println("子线程通知");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                test5Lock1.unlock();
            }
        }
    }

    static class OtherThread implements Runnable {

        @Override
        public void run() {
            test5Lock1.lock();
            try {
                System.out.println("其他线程等待通知");
                condition.await();
                System.out.println("其他线程恢复运行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                test5Lock1.unlock();
            }
        }
    }

    /**
     * 通过 ReentrantLock 的 Condition 实现阻塞队列
     */
    private void test6() {
        MyBlockingQueue<Integer> queue = new MyBlockingQueue<>(2);
        for (int i = 0; i < 10; i++) {
            int data = i;
            new Thread(() -> {
                try {
                    queue.enqueue(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        }
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    queue.dequeue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    class MyBlockingQueue<E> {

        int size;//阻塞队列最大容量

        ReentrantLock lock = new ReentrantLock();

        LinkedList<E> list = new LinkedList<>(); //队列底层实现

        Condition notFull = lock.newCondition(); //队列满时的等待条件
        Condition notEmpty = lock.newCondition(); //队列空时的等待条件

        MyBlockingQueue(int size) {
            this.size = size;
        }

        void enqueue(E e) throws InterruptedException {
            lock.lock();
            try {
                while (list.size() == size) { //队列已满,在notFull条件上等待
                    notFull.await();
                }
                list.add(e); //入队:加入链表末尾
                System.out.println("入队：" + e);
                notEmpty.signal(); //通知在notEmpty条件上等待的线程
            } finally {
                lock.unlock();
            }
        }

        E dequeue() throws InterruptedException {
            E e;
            lock.lock();
            try {
                while (list.size() == 0) { //队列为空,在notEmpty条件上等待
                    notEmpty.await();
                }
                e = list.removeFirst(); //出队:移除链表首元素
                System.out.println("出队：" + e);
                notFull.signal(); //通知在notFull条件上等待的线程
                return e;
            } finally {
                lock.unlock();
            }
        }

    }
}