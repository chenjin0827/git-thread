package com.xiangxue.thread.my.ch1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 新启线程的三种方式
 */
public class MyNewThread {
    private static class UseRun implements Runnable {
        @Override
        public void run() {
            System.out.println("我是实现Runnable新启的线程");
        }
    }

    private static class UseThread extends Thread {
        @Override
        public void run() {
            System.out.println("我是继承Thread新启的线程");
        }
    }

    /**
     * 带返回值的
     */
    private static class UseCall implements Callable<String> {
        @Override
        public String call() throws Exception {
            String s = "我是实现Callable新启的线程";
            System.out.println(s);
            //测试futureTask.get()是一个阻塞方法
            Thread.sleep(2000);
            System.out.println("等待2秒钟，模拟业务执行");
            return "我是线程"+Thread.currentThread().getId()+"返回值";
        }
    }

public static void main (String args[]) throws ExecutionException, InterruptedException {
    new Thread(new UseRun()).start();
    new UseThread().start();
    UseCall useCall = new UseCall();
    FutureTask futureTask=new FutureTask(useCall);
    FutureTask futureTask2=new FutureTask(useCall);
    new Thread(futureTask).start();
    new Thread(futureTask2).start();
    //futureTask.get()是一个阻塞的方法，会等拿到返回值才会进行返回
    System.out.println(futureTask.get());
    System.out.println(futureTask2.get());
    System.out.println("拿到返回值，结束");

}
}
