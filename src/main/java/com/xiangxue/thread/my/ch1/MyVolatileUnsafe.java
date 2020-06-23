package com.xiangxue.thread.my.ch1;

import com.xiangxue.tools.SleepTools;

/**
 * Volatile的线程不安全
 * 只适合一个线程写多个线程读的情况
 */
public class MyVolatileUnsafe {

    private static class VolatileVar implements Runnable {
        private volatile int a = 0;

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            a = a++;
            System.out.println(threadName + ":======" + a);
            SleepTools.ms(50);
            a = a + 1;
            System.out.println(threadName + ":======" + a);
        }
    }


    public static void main(String[] args) {
        Thread thread = new Thread(new VolatileVar());
        Thread thread2 = new Thread(new VolatileVar());
        Thread thread3 = new Thread(new VolatileVar());
        Thread thread4 = new Thread(new VolatileVar());
        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();

    }
}
