<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Form table</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/servlet/DoFormServlet" method="post">
		<input type="text" name="username"  /></br>
		<input type="hidden" name="Token" value="${TokenNum }"/>
		<input type="submit" value="提交" id="submit" onclick="denyDoubleClick()"/>
	</form>
	
	
	<script type="text/javascript">
		function denyDoubleClick(){
			document.getElementById("submit").disabled = "disabled";
		}
	</script>
</body>
</html>