package com.xiangxue.ch2.future;

import com.xiangxue.tools.SleepTools;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 知识点：
 出现异常捕获的地方重新打中断标记
 功能逻辑代码应该包含在判断!Thread.currentThread().isInterrupted()  之内
 */
public class UseFuture2 {
	
	/*实现Callable接口，允许有返回值*/
	private static class UseCallable implements Callable<Integer>{

		private int sum;
		@Override
		public Integer call() {
			System.out.println("Callable子线程开始计算");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();//出现异常 重新置为中断  功能应该放判断里面
				e.printStackTrace();
			}
			while(!Thread.currentThread().isInterrupted()){
				for(int i=0;i<5000;i++) {
					sum = sum+i;
				}
				System.out.println("Callable子线程计算完成，结果="+sum);
			}

			return sum;
		}

	}
	
	public static void main(String[] args) 
			throws InterruptedException, ExecutionException {
		
		UseCallable useCallable = new UseCallable();
		FutureTask<Integer> futureTask = new FutureTask<Integer>(useCallable);
		new Thread(futureTask).start();
		Random r = new Random();
		SleepTools.second(1);
		if(r.nextBoolean()) {//随机决定是获得结果还是终止任务
			System.out.println("Get UseCallable result = "+futureTask.get());
		}else {
			System.out.println("中断计算");
			futureTask.cancel(true);
		}
		
	}

}
