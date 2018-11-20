package com.example.demo.concurrent.atomic.referent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class AtomicReferenceDemo2 {
	static AtomicReference<Integer> mReference = new AtomicReference<Integer>();
	static ReentrantLock lock = new ReentrantLock();
	static int count = 0;
	static{
		mReference.set(0);
	}
	public static class AddThread implements Runnable{
		@Override
		public void run() {
			for (int i = 0; i < 10000; i++) {
				int m = mReference.get();
				if(!mReference.compareAndSet(m, m + 1)){
					lock.lock();
					count++;
					lock.unlock();
				};
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
		System.out.println(mReference.get());
		System.out.println(count);
		System.out.println(mReference.get() + count);
	}
}
