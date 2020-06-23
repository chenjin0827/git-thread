package com.xiangxue.thread.my.ch1;

import com.xiangxue.tools.SleepTools;

/**
 * Volatile的线程不安全
 * 只适合一个线程写多个线程读的情况
 * 使用Volatile会将数据实时同步到主内存
 */
public class MyVolatileUnsafe {

    private static class VolatileVar implements Runnable {
        private volatile int a = 0;

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            a = a++;
            System.out.println(threadName + ":======" + a);
            SleepTools.ms(100);
            a = a + 1;
            System.out.println(threadName + ":======" + a);
        }
    }


    public static void main(String[] args) {
        VolatileVar v = new VolatileVar();
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
