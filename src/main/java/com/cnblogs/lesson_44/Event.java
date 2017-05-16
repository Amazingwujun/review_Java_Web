package com.cnblogs.lesson_44;

public class Event<T> {
	private T source;

	public Event(T t){
		this.source = t;
	}
	
	public void setSource(T source) {
		this.source = source;
	}

	public T getSource() {
		return source;
	}
}
