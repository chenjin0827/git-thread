package com.xiangxue.thread.my.ch1;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 存在中断异常时，如果确实要中断线程。应该在catch中重新执行一次interrupt();
 * 因为中断异常会将中断标志位重新置为false
 */
public class MyHasInterrputException {
    private static SimpleDateFormat formater
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss_SSS");

    private static class UseThread extends Thread {
        public UseThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            while (!isInterrupted()) {
                try {
                    System.out.println("UseThread:" + formater.format(new Date()));
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println(threadName + " 捕获到异常，中断标志位为 "
                            + isInterrupted() + " at "
                            + (formater.format(new Date())));
                    interrupt();//重新调用一次 interrupt()进行线程中断，不然中断标志位此处被重置为false，线程就不会停下来了
                    e.printStackTrace();
                }
                System.out.println(threadName);
            }
            System.out.println(threadName + " 中断标志位位==== "
                    + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread endThread = new UseThread("MyHasInterrputEx");
        endThread.start();
        System.out.println("Main:" + formater.format(new Date()));
        Thread.sleep(800);
        System.out.println("main方法开始中断线程===");
        endThread.interrupt();
    }

}
