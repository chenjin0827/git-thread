package com.xiangxue.thread.my.ch2;

import com.xiangxue.tools.SleepTools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 使用分而治之求和
 */


public class MySumArray {
    private static class MySumTask extends RecursiveTask<Integer> {

        private final static int THRESHOLD = MyMakeArray.ARRAY_LENGTH / 10;//阈值定义为要计算数组的十分之一大小
        private int[] src; //表示我们要实际统计的数组
        private int fromIndex;//开始统计的下标
        private int toIndex;//统计到哪里结束的下标

        public MySumTask(int[] src, int fromIndex, int toIndex) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        protected Integer compute() {
            //当结束下标-开始小标<阈值，进行计算
            if (toIndex - fromIndex < THRESHOLD) {
                int count = 0;
                for (int i = fromIndex; i <= toIndex; i++) {
//                    SleepTools.ms(1);
                    count = count + src[i];
                }
                return count;
            } else {
                //fromIndex....mid....toIndex
                //1...................70....100
                int mid = (fromIndex + toIndex) / 2;
                MySumTask left = new MySumTask(src, fromIndex, mid);
                MySumTask right = new MySumTask(src, mid + 1, toIndex);
                //子任务交给pool去执行
                invokeAll(left, right);
                //调用join方法获取返回值
                return left.join() + right.join();
            }
        }
    }


    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int[] src = MyMakeArray.makeArray();
        MySumTask innerFind = new MySumTask(src, 0, src.length - 1);
        long start = System.currentTimeMillis();
        System.out.println("开始计算");
        pool.invoke(innerFind);//同步调用
        System.out.println("Task is Running.....");
        System.out.println("The count is " + innerFind.join()
                + " spend time:" + (System.currentTimeMillis() - start) + "ms");

    }
}
