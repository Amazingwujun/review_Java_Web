package com.cnblogs.lesson_48;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface WebServlet {
	// 外界访问的URL
	String value();

	// servlet的名字
	String name() default "";

	// 描述
	String description() default "";

	// servlet名字
	String displayName() default "";

	// servlet访问的URL
	String[] urlPattern() default {};

	// init参数
	WebInitParam[] initParam() default {};

}
