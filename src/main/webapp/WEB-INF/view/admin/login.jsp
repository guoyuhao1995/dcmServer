<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		
		<title>登录</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/assets/css/login/reset.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/assets/css/login/denglu.css"/>
		<script src="${ctx}/static/assets/js/jquery.js"></script>
	</head>
	<body>
		<div class="box">
			<div class="main">
				<div class="logo">
					<img src="${ctx}/static/assets/css/images/login/logo.png"/>
				</div>
					<div style="display: none;">
						<input class='fn-hide' type="text" />
						<input style="width: 60%; padding-left: 10%;" type="password" name="password" autocomplete="off"  placeholder="请输入密码" />
					</div>
				<form id="frm-login" action="${ctx}/checkLogin" method="post">
					<div class="message">
						<p><input type="text" name="userName" id="userName" value="${name }" autocomplete="off" placeholder="请输入用户名" /></p>
						<p>
							<input style="width: 60%; padding-left: 10%;" type="text" name="password" autocomplete="off" id="password" value="${pwd }" placeholder="请输入密码" />
							<img class="yan" src="${ctx}/static/assets/css/images/login/yan.png"/>
						</p>
					</div>
						<p style="color: red;border: 0px;">${error }</p>
					<div class="remember">
						<span class="gou" id="remember">√</span>记住密码
						<input id="isremember" name="remember" type="hidden" value="1"/>
					</div>
				</form>
				<div class="go" id="toLogin">
					<img src="${ctx}/static/assets/css/images/login/go.png"/>
				</div>
			</div>
		</div>
	</body>
<script type="text/javascript">
	$(function(){
		$("#password").val("${pwd}");
		document.getElementById("password").type="password";
	});
	$(".yan").mousedown(function(){
		$(this).siblings().prop("type","text");
	});
	$(".yan").mouseup(function(){
		$(this).siblings("input").prop("type","password");
	});
	$(".gou").click(function(){
		if ($(this).text()!=" ") {
			$(this).text(" ");
			$("#isremember").val(0);
		}else{
			$(this).text("√");
			$("#isremember").val(1);
		}
	});
	$("#toLogin").click(function(){
		var username =$("#userName");
		var password =$("#password");
		if(username == ''){
			alert("用户名不能为空");
			return false;
		}
		if(password == ''){
			alert('密码不能为空');
			return false;
		}
		$('#frm-login').submit();
	});
</script>
</html>
