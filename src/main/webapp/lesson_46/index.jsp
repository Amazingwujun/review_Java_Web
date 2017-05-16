<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>首页</title>       
 </head>
 <body>
 	<form action="${pageContext.request.contextPath }/servlet/loginServlet_46" method="post">
 		用户名:&nbsp;&nbsp;<input name="username" type="text"/><br/>
 		密码    :&nbsp;&nbsp;<input name="password" type="password"/><br/>
 		<input type="submit" value="提交"/>
 	</form>
 </body>
</html>