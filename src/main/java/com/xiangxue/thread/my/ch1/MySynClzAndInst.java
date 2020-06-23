package com.xiangxue.thread.my.ch1;

import com.xiangxue.tools.SleepTools;

/**
 * 测试synchronized关键字的对象锁和类锁
 */
public class MySynClzAndInst {


    //使用类锁的线程
    private static class SynClass extends Thread {
        @Override
        public void run() {
            System.out.println("TestClass is running...");
            synClass();
        }


    }

    //使用对象锁的线程
    private static class InstanceSyn implements Runnable {
        private MySynClzAndInst mySynClzAndInst;

        public InstanceSyn(MySynClzAndInst mySynClzAndInst) {
            this.mySynClzAndInst = mySynClzAndInst;
        }

        @Override
        public void run() {
            System.out.println("TestInstance is running..." + mySynClzAndInst);
            mySynClzAndInst.instance();
        }
    }

    //使用对象锁的线程
    private static class Instance2Syn implements Runnable {
        private MySynClzAndInst mySynClzAndInst;

        public Instance2Syn(MySynClzAndInst mySynClzAndInst) {
            this.mySynClzAndInst = mySynClzAndInst;
        }

        @Override
        public void run() {
            System.out.println("TestInstance2 is running..." + mySynClzAndInst);
            mySynClzAndInst.instance();
        }
    }


    //由于使用到的static  ，每个static都是和类对象进行绑定，这里是类锁
    private static synchronized void synClass() {
        SleepTools.second(1);
        System.out.println(Thread.currentThread().getId() + "synClass going...");
        SleepTools.second(1);
        System.out.println(Thread.currentThread().getId() + "synClass end");
    }

    //锁对象
    private synchronized void instance() {
        SleepTools.second(3);
        System.out.println(Thread.currentThread().getId() + "synInstance is going..." + this.toString());
        SleepTools.second(3);
        System.out.println(Thread.currentThread().getId() + "synInstance ended " + this.toString());
    }

    //锁对象
    private synchronized void instance2() {
        SleepTools.second(3);
        System.out.println(Thread.currentThread().getId() + "synInstance2 is going..." + this.toString());
        SleepTools.second(3);
        System.out.println(Thread.currentThread().getId() + "synInstance2 ended " + this.toString());
    }

    public static void main(String[] args) {
        MySynClzAndInst mySynClzAndInst = new MySynClzAndInst();

        //测试类锁
        /*SynClass synClass = new SynClass();
        synClass.start();*/
        //测试类锁结束
//        开始测试对象锁
        Thread thread = new Thread(new InstanceSyn(mySynClzAndInst));
//        Thread thread2 = new Thread(new InstanceSyn(mySynClzAndInst));
        Thread thread2 = new Thread(new Instance2Syn(mySynClzAndInst));
        thread.start();
        thread2.start();

    }
}
