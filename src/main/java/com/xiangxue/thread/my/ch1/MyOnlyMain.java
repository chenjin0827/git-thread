package com.xiangxue.thread.my.ch1;


import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 演示java天生就是多线程
 * 进程和线程的关联：
 * 进程是：程序运行资源分配的最小单位，进程内部有多大线程，会共享这个进程的资源
 * 线程是：cpu调度的最小单位，必须依赖进程而存在
 *
 * 并行：同一时刻，可以同时处理事情的能力
 * 并发：单位时间内可以处理事情的能力
 *
 * 高并发变成的意义、好处和注意事项：
 * 好处：充分利用cpu的资源，加快用户响应，程序模块化，异步化
 * 问题：线程共享资源，存在冲突。容易导致死锁。启用过多的线程，容易搞垮机器
 *
 * 对sleep和yield的理解：
 * sleep就是线程休眠了，一直到时间结束前，都不参与竞争了
 * yield就是线程让出当前执行的机会，重新进行下一轮的竞争
 */
public class MyOnlyMain {

    public static void main(String[] args) {

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false,false);
        for(ThreadInfo t:threadInfos){
            System.out.println("本次线程名为==="+t.getThreadName()+",当前线程id为===="+
                    t.getThreadId()+",当前线程状态为==="+t.getThreadState());
        }
    }

}
