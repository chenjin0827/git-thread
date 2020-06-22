package com.xiangxue.thread.my.ch1;


import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 演示java天生就是多线程
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
