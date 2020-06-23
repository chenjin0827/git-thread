package com.xiangxue.thread.my.ch1;

/**
 *
 * 等待和通知的标准范式
 等待方：
 1、	获取对象的锁；
 2、	循环里判断条件是否满足，不满足调用wait方法，
 3、	条件满足执行业务逻辑
 通知方来说
 1、	获取对象的锁；
 2、	改变条件
 3、	通知所有等待在对象的线程

 */
public class TestWaitAndNotify {


    private  static class Express {
        public final static String CITY = "ShangHai";
        private int km;/*快递运输里程数*/
        private String site;/*快递到达地点*/

        public Express() {
        }

        public Express(int km, String site) {
            this.km = km;
            this.site = site;
        }

        /* 变化公里数，然后通知处于wait状态并需要处理公里数的线程进行业务处理*/
        public synchronized void changeKm(){
            this.km = 101;
            notifyAll();
            //其他的业务代码

        }

        /* 变化地点，然后通知处于wait状态并需要处理地点的线程进行业务处理*/
        public synchronized void changeSite(){
            this.site = "BeiJing";
            notify();//此处唤醒对象就是当前的实例化的对象
        }

        public synchronized void waitKm(){
            while(this.km<=100) {
                try {
                    wait();
                    System.out.println("check km thread["+Thread.currentThread().getId()
                            +"] is be notifed.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("the km is"+this.km+",I will change db.");

        }

        public synchronized void waitSite(){
            while(CITY.equals(this.site)) {
                try {
                    wait();
                    System.out.println("check site thread["+Thread.currentThread().getId()
                            +"] is be notifed.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("the site is"+this.site+",I will call user.");
        }
    }
    /*检查里程数变化的线程,不满足条件，线程一直等待*/
    private static class CheckKm extends Thread{
        @Override
        public void run() {
            express.waitKm();
        }
    }

    /*检查地点变化的线程,不满足条件，线程一直等待*/
    private static class CheckSite extends Thread{
        @Override
        public void run() {
            express.waitSite();
        }
    }
    private static Express express = new Express(0,Express.CITY);
    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<3;i++){//三个线程
            new CheckSite().start();
        }
        for(int i=0;i<3;i++){//里程数的变化
            new CheckKm().start();
        }

        Thread.sleep(1000);
        express.changeKm();//快递地点变化
    }
}
