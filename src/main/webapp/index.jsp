<%@ page language="java" contentType="text/html; charset=utf8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<script type="text/javascript">
	function chSrc(obj) {
		document.getElementById(obj.id).src = "${pageContext.request.contextPath }/checkNumber?"+Math.random()
				;
	}
	
	var isSubmit = false;
	
	function doSubmit(){
		if(isSubmit==false){
			isSubmit = true;
			return true;
		}else{
			return false;
		}
		
	}
	
	function disableButton(obj){
		document.getElementById(obj.id).disabled= "disabled";
	}
	
</script>
</head>
<body>
	<div>
	
		<form action="${pageContext.request.contextPath }/check" method="post" >
			<table>
				<tr>
					<td><span><img id="nani" alt="看不清楚，换一张"
							src="${pageContext.request.contextPath }/checkNumber"
							onclick="chSrc(this)" /></span><input name="checknum" type="text" /></td>
					<td><input  id="submit" type="submit" onclick="disableButton(this)"/></td>
				</tr>
				<span>${message }</span>
			</table>
		</form>
	</div>

</body>
</html>