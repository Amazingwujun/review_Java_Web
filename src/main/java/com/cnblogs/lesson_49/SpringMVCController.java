package com.cnblogs.lesson_49;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SpringMVCController {

	@RequestMapping("/servlet/spring")
	public View action() {
		HttpServletRequest request = WebContext.requestHolder.get();
		HttpServletResponse response = WebContext.responseHolder.get();

		View view = new View("/lesson_50/fileUpload.jsp");
		view.setDispatcherAction(View.DISPATCHER_FORWARD);
		
		
		return view;
	}
}
