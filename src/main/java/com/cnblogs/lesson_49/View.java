package com.cnblogs.lesson_49;

public class View {
	//跳转路径
	private String uri;
	//
	private String dispatcherAction = "FORWARD";
	
	public View(String uri){
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDispatcherAction() {
		return dispatcherAction;
	}

	public void setDispatcherAction(String dispatcherAction) {
		this.dispatcherAction = dispatcherAction;
	}
	
}
