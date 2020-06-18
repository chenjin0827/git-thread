package com.xiangxue.ch1.safeend;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：如何安全的中断线程
 */
public class EndThread {
	
	private static class UseThread extends Thread{
		
		public UseThread(String name) {
			super(name);
		}
		
		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();
			//子线程必须处理中断，不能main方法中的interrupt 也不管用
			while(!isInterrupted()) {
				System.out.println(threadName+" is run!");
			}
			System.out.println(threadName+" interrput flag is "+isInterrupted());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread endThread = new UseThread("endThread");
		endThread.start();
		Thread.sleep(20);
		endThread.interrupt();

	}

}
