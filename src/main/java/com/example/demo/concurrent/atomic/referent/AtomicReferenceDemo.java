package com.example.demo.concurrent.atomic.referent;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {
	static Money money = new Money(0);
	static AtomicReference<Money> mReference = new AtomicReference<Money>(money);
	public static class AddThread implements Runnable{
		@Override
		public void run() {
			for (int i = 0; i < 10000; i++) {
				Money prev = mReference.get();
				mReference.compareAndSet(prev, prev.add());
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
		System.out.println(mReference.get().getValue());
		
	}
}
