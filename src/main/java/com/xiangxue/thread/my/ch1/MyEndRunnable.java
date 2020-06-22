package com.xiangxue.thread.my.ch1;

public class MyEndRunnable {

    private static class UseRunnable implements Runnable {

        @Override
        public void run() {

            String threadName = Thread.currentThread().getName();
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(threadName + " is run!");
            }
            System.out.println(threadName + " interrput flag is "
                    + Thread.currentThread().isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {


        UseRunnable useRunnable = new UseRunnable();
        Thread thread = new Thread(useRunnable);
        thread.start();
        Thread.sleep(5);
        thread.interrupt();

    }
}
