package com.xiangxue.thread.my.ch1;


import java.sql.Connection;
import java.util.LinkedList;

/**
 * 等待超时模式实现一个连接池
 假设  等待时间时长为T，当前时间now+T以后超时

 long  overtime = now+T;
 long remain = T;//等待的持续时间
 while(result不满足条件&& remain>0){
 wait(remain);
 remain = overtime – now;//等待剩下的持续时间
 }
 return result;

 */
public class MyDBPool {

    //数据库池的容器
    private static LinkedList<Connection> pool = new LinkedList<>();

    //初始化数据库
    public MyDBPool(int initalSize) {
        if (initalSize > 0) {
            for (int i = 0; i < initalSize; i++) {
                pool.addLast(MySqlConnectImpl.fetchConnection());
            }
        }
    }

    //在mills时间内还拿不到数据库连接，返回一个null
    public Connection fetchConn(long mills) throws InterruptedException {
       //超时等待模型
        synchronized (pool) {
            if (mills<0) {
                while(pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            }else {
                long overtime = System.currentTimeMillis()+mills;
                long remain = mills;
                while(pool.isEmpty()&&remain>0) {
                    pool.wait(remain);//如果提前被唤醒，下面需要重新计算下等待时间
                    remain = overtime - System.currentTimeMillis();
                }
                Connection result  = null;
                if(!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }


    }

    //放回数据库连接
    public void releaseConn(Connection conn) {
        if (conn != null) {
            synchronized (pool) {
                pool.addLast(conn);
                pool.notifyAll();
            }
        }
    }


}