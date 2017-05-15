package com.cnblogs.lesson_48;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(ElementType.PARAMETER)
public @interface WebInitParam {
	String value() default "";

	String name() default "";
}
