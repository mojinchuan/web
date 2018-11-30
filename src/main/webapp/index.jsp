<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="java.util.*,martin.simple.domain.UserInfo"%>
<html>
<head>
<script src="/js/jquery-1.8.3.min.js" type="text/javascript" /></script>
<script type="text/javascript">
function testANDandComma(){
	var e = $("#password").val();
	return 1 == $("#loginname").length && jQuery("#name").val(jQuery("#loginname").val()),
	e	
}
function clickA(){
	alert(testANDandComma());
}
</script>
</head>
<body>
<h2>用户信息管理</h2>
<h5>注：点击行头紫色字段排序。</h5>
<table>
        <tr>  
             <td style ="color:purple" align = "center" title="点击按ID排序" onclick="document.location.href='user?op=query&sortby=Id'">id</td>  
             <td style ="color:purple" align = "center" title="点击按登录名排序" onclick="document.location.href='user?op=query&sortby=Loginname'">登录名</td>  
             <td style ="color:blue" align = "center">密码</td>  
             <td style ="color:blue" align = "center">姓名</td>
         </tr>
<c:forEach items="${list}" var="user" varStatus="vs">  
        <tr>  
             <td align = "center">${user.id}</td>  
             <td align = "center">${user.loginname}</td>  
             <td align = "center">${user.password}</td>  
             <td align = "center">${user.name}</td>
         </tr>
</c:forEach>
</table>
<br/>
<h5>添加用户信息：</h5>
<form action="user" method="post">
	<input type="hidden" name="op" value="put">
	id:<input type="number" name="id" />
	登录名：<input type="text" name="loginname" id="loginname"/>
	姓名：<input type="text" name="name" id="name"/>
	密码：<input type="text" name="password" id="password"/>
	<input type="button" onClick="clickA()"/>
</form> 
</body>
</html>
