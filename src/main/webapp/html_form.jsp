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
			<legend>html�ı�Ԫ��</legend>
			<form action="${pageContext.request.contextPath }/servlet/RequestDemo03" method="get">
				��&nbsp;&nbsp;��(�ı���):
				<input id ="uid" type="text" name="userid" value="NO." onfocus="clsStr(this)"/><br/>
				�û������ı���:
				<input id="name" type="text" name="username" value="�������û���" onfocus="clsStr(this)" /><br/>
				��&nbsp;&nbsp;��(�����)��
				<input id="psw" type="password" name="password" value="����������" onfocus="clsStr(this)"/><br/>
				�Ա𣨵�ѡ�򣩣�
				<input type="radio"  name="sex" value="��" checked="checked"/>��
				<input type="radio" name="sex"	value="Ů"/>Ů<br/>
				��&nbsp;&nbsp;�ţ������򣩣�
				<select name="dept">
					<option value="������" >������</option>
					<option value="���۲�" >���۲�</option>
					<option value="����" >����</option>
				</select><br/>
				��&nbsp;&nbsp;Ȥ����ѡ�򣩣�
				<input type="checkbox" name="inte" value="С˵" />С˵
				<input type="checkbox" name="inte" value="��Ϸ"/>��Ϸ
				<input type="checkbox" name="inte" value="��Ӱ"/>��Ӱ
				<input type="checkbox" name="inte" value="�ܲ�"/>�ܲ�
				<br/>
				˵&nbsp;&nbsp;�����ı��򣩣�
				<textarea name="note" rows="5" cols="35"></textarea>
				<!-- ���ر� -->
				<input type="hidden" name="hiddenField" value="hiddenValue" />
				<input type="submit" value="�ύ"/>
				<input type="reset" value="����"  />
				
				
			</form>
		</fieldset>
	</div>
	
</body>
</html>