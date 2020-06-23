package com.xiangxue.thread.my.ch1;

/**
 * ThreadLocal的使用
 * 线程变量。可以理解为是个map，类型 Map<Thread,Integer>
 * 线程之间的变量都是独立的，线程之间不受影响
 */
public class MyUseThreadLocal {
    //可以理解为 一个map，类型 Map<Thread,Integer>
    static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 1);

    /**
     *类说明：测试线程，线程的工作是将ThreadLocal变量的值变化，并写回，看看线程之间是否会互相影响
     */
    public static class TestThread implements Runnable{
        int id;
        public TestThread(int id){
            this.id = id;
        }
        public void run() {
            System.out.println(Thread.currentThread().getName()+":startooo");
            Integer s = threadLocal.get();//获得变量的值
            s = s+id;
            threadLocal.set(s);
            System.out.println(Thread.currentThread().getName()+":"
                    +threadLocal.get());
        }
    }

    /**
     * 运行3个线程
     */
    public void startThreadArray(){
        Thread[] runs = new Thread[3];
        for(int i=0;i<runs.length;i++){
            runs[i]=new Thread(new TestThread(i));
        }
        for(int i=0;i<runs.length;i++){
            runs[i].start();
        }
    }

    public static void main(String[] args){
        MyUseThreadLocal test = new MyUseThreadLocal();
        test.startThreadArray();
    }
}


