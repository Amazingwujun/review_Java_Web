package com.cnblogs.lesson_49;

@Controller
public class View {
	// 跳转路径
	private String uri;
	// 服务器内部跳转
	public static final String DISPATCHER_FORWARD = "FORWARD";
	// 请求重定向
	public static final String DISPATCHER_REDIRECT = "REDIRECT";

	//
	private String dispatcherAction = DISPATCHER_FORWARD;

	public View(String uri) {
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
