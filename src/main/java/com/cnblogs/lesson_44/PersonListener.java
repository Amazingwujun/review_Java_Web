package com.cnblogs.lesson_44;

public interface PersonListener<T> {
	public void doEat(Event<T> e);
	
	public void doRun(Event<T> e);
}
