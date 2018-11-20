package com.example.demo.concurrent.atomic.referent;

public class NormalDemo {

	static Money money = new Money(0);
	
	public static class AddThread implements Runnable{
		@Override
		public void run() {
			for (int i = 0; i < 10000; i++) {
				money.setValue(money.getValue() + 1);
			}
			
		}
	}
	
	public static void main(String[] args) {
		Thread[] arr = new Thread[3];
		for (int i = 0; i < arr.length; i++) {
			Thread r = new Thread(new AddThread());
			arr[i] = r;
		}
		
		for (int i = 0; i < arr.length; i++) {
			arr[i].start();
		}
		
		for (int i = 0; i < arr.length; i++) {
			try {
				arr[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(money.getValue());
		
	}
}
