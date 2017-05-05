<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>charset</title>       
 </head>
 <body>
 	<c:url value="/servlet/CharsetFilterDemo" var="servletDemo1">
 		<c:param name="username">想你的俊</c:param>
 	</c:url>
 	<a href="${servletDemo1 }">doget</a>
 
 	<form action="${pageContext.request.contextPath }/servlet/CharsetFilterDemo" method="post">
 		<input name="username" type="text"/>
 		<input type="submit" value="提交" />
 	</form>
 </body>
</html>