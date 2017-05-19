<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/jun" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>文件下载页面</title>       
 </head>
 <body>
 	<c:foreach var="fileName" target="${fileList }">
 		<span></span><a href="" ></a>
 	
 	</c:foreach>
 	
 	
 </body>
</html>