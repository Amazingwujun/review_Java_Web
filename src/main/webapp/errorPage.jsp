<%@ page language="java" contentType="text/html; charset=utf8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>错误信息友好提示页面</title>
</head>
<body>
	出错了，请联系管理员处理。<br/>
	<%=exception.getMessage() %>
</body>
</html>