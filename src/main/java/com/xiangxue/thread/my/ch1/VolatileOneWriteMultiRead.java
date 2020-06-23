package com.xiangxue.thread.my.ch1;

import com.xiangxue.tools.SleepTools;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 测试Volatile一个线程写，多个线程读
 * 结合wait和notify使用
 * 应该尽量使用notifyAll，使用notify因为有可能发生信号丢失的的情况
 */
public class VolatileOneWriteMultiRead {
    private volatile int a = 1;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    //   写线程
    private static class WriteThread extends Thread {
        private VolatileOneWriteMultiRead volatileOneWriteMultiRead;

        public WriteThread(String name, VolatileOneWriteMultiRead volatileOneWriteMultiRead) {
            super(name);
            this.volatileOneWriteMultiRead = volatileOneWriteMultiRead;
        }

        @Override
        public void run() {
            while (true) {
                SleepTools.second(2);//此处等待2秒，让所有线程都初始化完成，不然会出现第一轮数据读线程可能无法取到
                long id = Thread.currentThread().getId();
                int a = volatileOneWriteMultiRead.getA();
                synchronized (volatileOneWriteMultiRead) {
                    volatileOneWriteMultiRead.setA(++a);
                    System.out.println("写线程id" + id + "写入值a为" + volatileOneWriteMultiRead.getA());
                    volatileOneWriteMultiRead.notifyAll();
                }

            }


        }
    }

    //    读线程
    private static class ReadThread extends Thread {
        private VolatileOneWriteMultiRead volatileOneWriteMultiRead;

        public ReadThread(String name, VolatileOneWriteMultiRead volatileOneWriteMultiRead) {
            super(name);
            this.volatileOneWriteMultiRead = volatileOneWriteMultiRead;
        }

        @Override
        public void run() {
            synchronized (volatileOneWriteMultiRead) {
                while (true) {
                    try {
                        //此处可以添加个条件，  不满足条件就等待  只有一个需要唤醒的没有条件不要紧，多个唤醒就必须条件，不然
                        //不然使用notifyAll会出现误唤醒
                        volatileOneWriteMultiRead.wait();
                        long id = Thread.currentThread().getId();
                        System.out.println("读线程id" + id + "当前值a为" + volatileOneWriteMultiRead.getA());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }


        }
    }

    public static void main(String[] args) {
        VolatileOneWriteMultiRead volatileOneWriteMultiRead = new VolatileOneWriteMultiRead();
        WriteThread writeThread = new WriteThread("我是写线程", volatileOneWriteMultiRead);
        writeThread.start();
        ReadThread readThread = new ReadThread("我是读线程", volatileOneWriteMultiRead);
        ReadThread readThread2 = new ReadThread("我是读线程", volatileOneWriteMultiRead);
        ReadThread readThread3 = new ReadThread("我是读线程", volatileOneWriteMultiRead);
        ReadThread readThread4 = new ReadThread("我是读线程", volatileOneWriteMultiRead);
        readThread.start();
        readThread2.start();
        readThread3.start();
        readThread4.start();

    }
}
