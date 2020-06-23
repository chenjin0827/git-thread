package com.xiangxue.thread.my.ch1;

import com.xiangxue.tools.SleepTools;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用AtomicInteger实现线程安全
 * 注意使用getAndIncrement方法时，注意将返回值写回到一个变量中
 * 不然可能涉及++i和i++的问题，导致数据显示不对
 */



public class MyAtomicInteger {
    private static class AtomicIntegerVar implements Runnable {
        private AtomicInteger a = new AtomicInteger(0);

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            int andIncrement = a.getAndIncrement();
            System.out.println(threadName + ":======" + andIncrement);
            SleepTools.ms(100);
            int andIncrement2 = a.getAndIncrement();
            System.out.println(threadName + ":======" + andIncrement2);
        }
    }


    public static void main(String[] args) {
        AtomicIntegerVar v = new AtomicIntegerVar();
        Thread thread = new Thread(v);
        Thread thread2 = new Thread(v);
        Thread thread3 = new Thread(v);
        Thread thread4 = new Thread(v);
        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();

    }
}