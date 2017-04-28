<%@page import="java.util.ResourceBundle"%>
<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>Internationalization</title>       
 </head>
 <body>
 	<%
 		ResourceBundle bundle = ResourceBundle.getBundle("myproperties", request.getLocale());
 	%>
 	<form action="">
 		<%=bundle.getString("username") %>:&nbsp;&nbsp;<input name="username" type="text"/><br/>
 		<%=bundle.getString("password") %>:&nbsp;&nbsp;<input name="password" type="password" /><br/>
 		<input name="submit" type="submit" value="<%=bundle.getString("submit") %>"/>
 	</form>
 </body>
</html>