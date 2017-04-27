<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="/jun" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>foreach</title>       
 </head>
 <body>
 
 	 <%--使用out标签输出content属性的内容 --%>
          <c:out content="<a href='http://www.cnblogs.com'>访问博客园</a>"/>
          <hr/>
          <%--使用out标签输出 content属性的内容，内容中的html代码会进行转义处理--%>
          <c:out content="<a href='http://www.cnblogs.com'>访问博客园</a>" escapeHtml="true"/>
 
 	<c:HTMLFilter>
 		<a href="https://www.baidu.com/">百度</a>
 	</c:HTMLFilter>
 
 	<%
 		List<String> list = new ArrayList<>();
 		list.add("wujun");
 		list.add("yongxiu");
 		list.add("你的名字");
 		request.setAttribute("list", list);
 		

 	    //对象数组
 	    Integer intObjArr[] = new Integer[]{1,2,3};
 	    
 	    //基本数据类型数组
 	    int intArr[] = new int[]{4,5,6};
 	    
 	    //map集合
 	    Map<String,String> mapData = new HashMap<String,String>();
 	    mapData.put("a", "aaaaaa");
 	    mapData.put("b", "bbbbbb");
 	    
 	    //将集合存储到pageContext对象中
 	    pageContext.setAttribute("intObjArr", intObjArr);
 	    pageContext.setAttribute("intArr", intArr);
 	    pageContext.setAttribute("mapData", mapData);
 	%>
 	
 	<c:foreach target="${list }" var="var">
 		${var }<br/>
 	</c:foreach>
 	
 	<c:foreach target="${intObjArr }" var="ObjArr">
 		${ObjArr }<br/>
 	</c:foreach>
 	
 	<c:foreach target="${intArr }" var="Arr">
 		${Arr }<br/>
 	</c:foreach>
 	
 	<c:foreach target="${mapData }" var="Data">
 		${Data }<br/>
 	</c:foreach>
 	

 </body>
</html>