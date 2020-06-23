package com.xiangxue.thread.my.ch2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MySumArray2 extends RecursiveTask<Integer> {


    @Override
    protected Integer compute() {


        return null;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        MySumArray2 mySumArray2 = new MySumArray2();
        pool.invoke(mySumArray2);
    }
}
