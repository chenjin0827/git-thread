package com.xiangxue.thread.my.ch1;

/**
 * 安全中断Thread线程
 */
public class MyEndThread {
    private static class UseThread extends Thread{

        public UseThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            //子线程必须自己处理中断，不然main方法中的interrupt 也不管用，协作式处理，
            while(!isInterrupted()) {
                System.out.println(threadName+" is run!");
            }
            System.out.println(threadName+" interrput flag is "+isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UseThread useThread = new UseThread("测试安全中断继承Thread的线程");
        useThread.start();
        Thread.sleep(5);//休眠5ms之后中断线程
        useThread.interrupt();
    }
}
