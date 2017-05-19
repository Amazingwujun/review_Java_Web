<%@ page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>文件上传</title>
</head>
<body>
	<form method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath }/servlet/FileUploadHandle">
		File to upload: <input type="file" name="upfile"><br /> 
		Notes about the file: <input type="text" name="note"><br /> 
		<br /> <input type="submit" value="Press"> to upload the file!
	</form>
</body>
</html>