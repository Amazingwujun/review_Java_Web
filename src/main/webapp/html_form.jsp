<%@ page language="java" contentType="text/html; charset=gbk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
	<script type="text/javascript">
		function clsStr(obj){
			document.getElementById(obj.id).value="";
		}
	</script>
</head>
<body>
	<div>
		<fieldset style="width: 500px;">
			<legend>html的表单元素</legend>
			<form action="${pageContext.request.contextPath }/servlet/RequestDemo03" method="get">
				编&nbsp;&nbsp;号(文本框):
				<input id ="uid" type="text" name="userid" value="NO." onfocus="clsStr(this)"/><br/>
				用户名（文本框）:
				<input id="name" type="text" name="username" value="请输入用户名" onfocus="clsStr(this)" /><br/>
				密&nbsp;&nbsp;码(密码框)：
				<input id="psw" type="password" name="password" value="请输入秘密" onfocus="clsStr(this)"/><br/>
				性别（单选框）：
				<input type="radio"  name="sex" value="男" checked="checked"/>男
				<input type="radio" name="sex"	value="女"/>女<br/>
				部&nbsp;&nbsp;门（下拉框）：
				<select name="dept">
					<option value="技术部" >技术部</option>
					<option value="销售部" >销售部</option>
					<option value="财务部" >财务部</option>
				</select><br/>
				兴&nbsp;&nbsp;趣（复选框）：
				<input type="checkbox" name="inte" value="小说" />小说
				<input type="checkbox" name="inte" value="游戏"/>游戏
				<input type="checkbox" name="inte" value="电影"/>电影
				<input type="checkbox" name="inte" value="跑步"/>跑步
				<br/>
				说&nbsp;&nbsp;明（文本域）：
				<textarea name="note" rows="5" cols="35"></textarea>
				<!-- 隐藏表单 -->
				<input type="hidden" name="hiddenField" value="hiddenValue" />
				<input type="submit" value="提交"/>
				<input type="reset" value="重置"  />
				
				
			</form>
		</fieldset>
	</div>
	
</body>
</html>