<%@page import="com.cnblogs.lesson_45.JavaBeanListener2"%>
<%@page import="com.cnblogs.lesson_45.JavaBeanDemo1"%>
<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>Listener</title>       
 </head>
 <body>
 	<%
 		session.setAttribute("name", new JavaBeanListener2("jun"));
 		//session.removeAttribute("name");
 	%>
 </body>
</html>