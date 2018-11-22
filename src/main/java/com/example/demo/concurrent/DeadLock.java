package com.example.demo.concurrent;

public class DeadLock extends Thread{
	private static Object fork1 = new Object();
	private static Object fork2 = new Object();
	
	Object myfork;
	
	public DeadLock(Object fork, String name) {
		myfork = fork;
		this.setName(name);
	}
	
	@Override
	public void run() {
		if(myfork == fork1){
			synchronized (fork1) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				synchronized (fork2) {
					System.out.println(Thread.currentThread().getName() + " is eating");
				}
			}
			
			
		}else if(myfork == fork2){
			synchronized(fork2){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				synchronized (fork1) {
					System.out.println(Thread.currentThread().getName() + " is eating");
				}
			}
			
		}
	}

	
	public static void main(String[] args) {
		DeadLock d1 = new DeadLock(fork1, "哲学家A");
		DeadLock d2 = new DeadLock(fork2, "哲学家B");
		d1.start();
		d2.start();
	}
}
