package com.xiangxue.thread.my.ch1;

import com.xiangxue.tools.SleepTools;

import java.util.ArrayList;
import java.util.List;

/**
 *当集合里面数据大于10个时通知别的线程来读取
 * 取完清空集合
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
public class WaitAndNotifyListTest {
    private  static List list=new ArrayList<String>();
    //   写线程
    private static class WriteThread extends Thread {


        @Override
        public void run() {
            while (true) {
                SleepTools.ms(500);//此处等待2秒，让所有线程都初始化完成，不然会出现第一轮数据读线程可能无法取到
                long id = Thread.currentThread().getId();
                synchronized (list) {
                    list.add("1");
                    System.out.println("写线程id" + id + "集合目前大小为" + list.size());
                    list.notifyAll();
                }

            }


        }
    }

    //    读线程
    private static class ReadThread extends Thread {



        @Override
        public void run() {
            synchronized (list) {
                while(true){
                    while (list.size()<10) {
                        try {
                            //此处可以添加个条件，  不满足条件就等待  只有一个需要唤醒的没有条件不要紧，多个唤醒就必须条件，不然
                            //不然使用notifyAll会出现误唤醒
                            System.out.println("读线程开始等待");
                            list.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    long id = Thread.currentThread().getId();
                    System.out.println("读线程id" + id + "集合大小为" +list.size());
                    list.clear();
                }

            }
        }
    }

    public static void main(String[] args) {
        WriteThread writeThread = new WriteThread();
        ReadThread readThread = new ReadThread();
        writeThread.start();
        readThread.start();


    }
}
