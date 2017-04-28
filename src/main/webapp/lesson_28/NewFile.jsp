<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>??</title>       
 </head>
 <body>
 	<%
		List<String> list = new ArrayList<>();
 		list.add(0, "贝贝");
 		list.add(1, "晶晶");
 		list.add(2, "欢欢");
 		list.add(3, "莹莹");
 		list.add(4, "妮妮");
 		request.setAttribute("list",list);
 		
 		//request.getSession().setAttribute("user", "wujun");
 		request.getSession().removeAttribute("user");
	%>
	${request.contextPath}
	${user==null?"你尚未登录":user}<br/>
	${cookie.JSESSIONID.name }
	${!empty(list)}
	<c:out value="不指定end与begin的迭代"/><br/>
 	<c:forEach var="fuwa" items="${list }" >
 		<c:out value="${fuwa }"/><br/>
 	</c:forEach>
 	<hr>
 	<c:out value="指定end与begin的迭代"/><br/>
 	<c:forEach var="fuwa" items="${list }" end="4" begin="0" step="2" varStatus="nani">
 		<c:out value="${fuwa }"/><br/>
 	</c:forEach>
 	
 	<%-- <c:import url="https://www.baidu.com" charEncoding="utf8"></c:import> --%>
 	
 	<c:url value="http://www.baidu.com" var="url" scope="session">
 		<c:param name="kw" value="nani">jun</c:param>
 	</c:url>
 	<a href="${url }">baidu</a>
 	
 	
 </body>
</html>