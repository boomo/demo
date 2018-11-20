package com.example.demo.concurrent.atomic.referent;

import java.util.concurrent.locks.ReentrantLock;

public class Money {

	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	static ReentrantLock mylock = new ReentrantLock();
	public Money add(){
		mylock.lock();
		this.value++;
		mylock.unlock();
		return this;
	}

	public Money(int value) {
		super();
		this.value = value;
	}

}
