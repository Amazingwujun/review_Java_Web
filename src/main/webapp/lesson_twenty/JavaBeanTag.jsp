<%@ page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<jsp:useBean id="person" class="com.cnblogs.lesson_twenty.Person"
	scope="session"></jsp:useBean>
	<<jsp:setProperty property="" name="" />
<%
	person.setAge(27);
	person.setMarried(true);
	person.setName("伍俊");
	person.setSex("男");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>javabean标签使用</title>
</head>
<body>
	<h2>姓名： <%=person.getName() %></h2>
	<h2>年龄： <%=person.getAge() %></h2>
	<h2>性别： <%=person.getSex() %></h2>
	<h2>婚否： <%=person.isMarried()?"已婚":"未婚" %></h2>
</body>
</html>