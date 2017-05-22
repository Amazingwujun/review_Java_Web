<%@ page language="java" contentType="text/html; charset=UTF8"%>
<!DOCTYPE html>
<head>
<title>注册页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form
		action="${pageContext.request.contextPath}/servlet/RegisterServlet"
		method="post">
		用户名：<input type="text" name="username"><br /> 密码：<input
			type="password" name="password"><br /> 邮箱：<input type="text"
			name="email"><br /> <input type="submit" value="注册">
	</form>
</body>
</html>