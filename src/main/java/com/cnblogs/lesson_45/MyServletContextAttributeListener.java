package com.cnblogs.lesson_45;

import java.text.MessageFormat;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextAttributeListener implements ServletContextAttributeListener {

	@Override
	public void attributeAdded(ServletContextAttributeEvent event) {
		String msg = MessageFormat.format("Application域对象中添加了属性:{0}，属性值为:{1}"
				, event.getName()
				, event.getValue());
		System.out.println(msg);
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent event) {
		String msg = MessageFormat.format("Application域对象修改了属性:{0}，属性值为:{1}"
				, event.getName()
				, event.getValue());
		System.out.println(msg);
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent event) {
		String msg = MessageFormat.format("Application域对象中移除了属性:{0}，属性值为:{1}"
				, event.getName()
				, event.getValue());
		System.out.println(msg);
	}

}
