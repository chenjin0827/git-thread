package com.xiangxue.thread.my.ch1;

/**
 * 守护线程
 * 测试方法：当setDaemon(false)  ，主线程休眠5ms后将测试守护线程中断
 *          当setDaemon(true)  ，注释掉中断守护线程的代码，不然finally照样会进入
 *总结：守护线程被中断时，finally里面的代码可能会不执行
 */
public class MyDaemonThread {

    private static class UseThread extends Thread{
        @Override
        public void run() {
            try{
                while(!isInterrupted()){
                    System.out.println("我是测试守护进程的线程"+Thread.currentThread().getName());
                }
                System.out.println(Thread.currentThread().getName()
                        + " 中断标志位为== " + isInterrupted());
            }finally {
                System.out.println("执行到finally语句块中！");
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程开始执行");
        UseThread useThread = new UseThread();
        useThread.setName("测试守护线程的线程");
        useThread.setDaemon(true);
        useThread.start();
        Thread.sleep(5);
//        useThread.interrupt();
        System.out.println("主线程结束执行");

    }

}
