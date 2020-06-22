package com.xiangxue.thread.my.ch1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 测试slepp休眠时候是否是释放锁
 * Thread类中的静态方法sleep()，当一个执行中的线程调用了Thread的sleep()方法后，调用线程会暂时让出时间的执行权，这期间不参与cpu的调度，
 * 但是该线程持有的锁是不让出的。时间到了会正常返回，线程处于就绪状态，然后参与cpu调度，获取到cpu资源之后就可以运行。
 * 如果在睡眠期间，其他线程调用了该线程的interrup()的方法中断了该线程，则该线程会调用sleep方法的地方抛出InterruptedException异常而返回
 * sleep方法不释放锁，但是它不参与cpu的调度，其余不持有该锁的任务可以进行竞争
 */
public class SleepThreadDemo {
    private static Object lock = new Object();
//    static CountDownLatch latch=new CountDownLatch(1);
private static CyclicBarrier barrier
        = new CyclicBarrier(2);
    public static void main(String[] args)  {
        Thread threadA = new Thread(() -> {
            try {
                barrier.await();
                synchronized (lock) {
                    try {
                        System.out.println("A休眠2秒不放弃锁");
                        Thread.sleep(5);
                        System.out.println("A休眠2秒醒来");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        });

        Thread threadB = new Thread(() -> {
            try {
                barrier.await();
                synchronized (lock) {
                    System.out.println("B休眠2秒不放弃锁");
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("B休眠2秒醒来");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        });
        System.out.println("主线程执行");
        threadA.start();
        threadB.start();
        System.out.println("主线程结束");
    }
}
