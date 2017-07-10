<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>管理系统</title>
<meta name="description" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<link rel="stylesheet" href="${ctx}/static/assets/css/login/reset.css" />
<link rel="stylesheet" href="${ctx}/static/assets/css/login/manage.css" />

<link rel="stylesheet" href="${ctx}/static/main/assets/css/bootstrap.min.css" />
<script type="text/javascript">
	var ctx = '${ctx}';
</script>

<script src="${ctx}/static/js/jquery-2.1.4.min.js"></script>
<script src="${ctx}/static/main/assets/js/ace-extra.min.js"></script>
<script src="${ctx}/static/main/mainPlug1.js"></script>
<script src="${ctx}/static/main/assets/js/bootstrap.min.js"></script>
<script src="${ctx}/static/main/assets/js/ace-elements.min.js"></script>
<script src="${ctx}/static/assets/js/bootbox.js"></script>
<script src="${ctx}/static/main/sidebar-menu.js"></script>
<script src="${ctx}/static/main/main.js"></script>
<script src="${ctx}/static/js/bootstrapValidator.min.js"></script>
<script src="${ctx}/static/js/selectUi.js"></script>
<style type="text/css">
	body{margin:0; padding:0;}
</style>
<script type="text/javascript">
$(function() {
	$('#toUpdate').click(function() {
		$("#oldPassword").val('');
		$("#newPassword").val('');
		$("#againPassword").val('');
		$('#updatePwd').modal({
			show : true
		});
	});
	$("#logout").click(function(){
		location.href=ctx+"/logout";
	});
	$('#updatePwdForm').bootstrapValidator({
		excluded : [ ':disabled' ],
		message : '抱歉，您输入的数据无效',
		feedbackIcons : {
			required : 'glyphicon glyphicon-asterisk',
			valid : 'glyphicon',
			invalid : 'glyphicon',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			oldPassword : {
				validators : {
					notEmpty : {
						message : '旧密码不能为空'
					}
				}
			},
			newPassword : {
				validators : {
					notEmpty : {
						message : '新密码不能为空'
					}
				}
			},
			againPassword : {
				validators : {
					notEmpty : {
						message : '请确认密码不能为空'
					},identical: {
						field: 'newPassword',
						message: '两次输入密码不一致'
					}
				}
			}
		}
	}).on('success.form.bv', function(e, data) {
		e.preventDefault();
		var formData = $("#updatePwdForm").serialize();
		$.ajax({
			url : ctx + "/user/updatePwd",
			data : formData,
			type : 'post',
			success : function(data) {
				var returnData = JSON.parse(data);
				if (returnData.code == 1) {
					bootbox.alert({
						message : "<h4>修改成功，下次登陆生效</h4>",
						callback : function() {
							$("#45").modal("hide");
							$("#submit_update").disabled = false;
							$("#submit_update").attr("disabled",false);
							$("#updatePwd").modal("hide");
						}
					});
				}else {
					bootbox.alert({
						message : "<h4>修改失败,"+returnData.msg+"</h4>",
						callback : function() {
							$("#upload-report").modal("hide");
							$("#submit_update").disabled = false;
							$("#submit_update").attr("disabled",false);
							$("#updatePwd").modal("hide");
						}
					});
				}
			}
		});
	}); 
	
	var lists = document.getElementsByName("cdurl");
	if(lists.length > 0){
	var obj = document.getElementById("list_0");
	showView(lists[0].value,obj);
	}
	
});

</script>
<style type="text/css">
	.updateHand{
		cursor:pointer;
	}
</style>
</head>

<body>
		<div id="content" class="box">
			<div class="header">
				<div class="left">
					<img src="${ctx}/static/assets/css/images/login/log.png"/>
					<p>约克视康阅片平台</p>
					<ul class="nav">
					<c:forEach items="${list }" var="list" varStatus="status">
						<li id="list_${status.index }" style="cursor:pointer;" onclick="showView('${list.url}',this)">${list.name }</li>
						<input type="hidden" name="cdurl" value="${list.url }"/>
					</c:forEach>
					</ul>
				</div>
				<div class="right">
					<p class="updateHand" id="toUpdate">修改密码</p>
					<p class="updateHand" id="logout">退出登录</p>
					<p>用户：<span>${sessionScope.userinfo.name }<c:if test="${sessionScope.userinfo.roleId == 1 }">医生</c:if></span></p>
				</div>	
			</div>
			<!--眼底筛查主题内容-->
			<div>
				<iframe id="iframe" onload="changeFrameHeight()" scrolling="yes" frameborder="0" style="width:100%; overflow:hidden; position: absolute;top:57px;"></iframe>
			</div>
		</div>
		
		
		<!-- update password -->
		<div id="updatePwd" class="modal fade" tabindex="-1" data-backdrop="static">
			<form method="post" id="updatePwdForm">
				<div class="modal-dialog" style="width: 500px;">
					<div class="modal-content">
						<span class="counter pull-right"></span>
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h3 class="smaller lighter blue no-margin">
								<span id="">修改密码</span>
							</h3>
						</div>
						<div class="panel-body no-padding">
							
								<div class="form-group">
									<label for="name" class="col-sm-4 control-label">输入原密码：</label>
									<div class="col-sm-8">
										<input type="password"class="form-control" name="oldPassword" id="oldPassword" value="" required />
									</div>
								</div>
								
								<div class="form-group">
									<label for="name" class="col-sm-4 control-label">输入新密码：</label>
									<div class="col-sm-8">
										<input type="password"class="form-control" id="newPassword" name="newPassword" value="" required />
									</div>
								</div>
								
								<div class="form-group">
									<label for="name" class="col-sm-4 control-label">确认密码：</label>
									<div class="col-sm-8">
										<input type="password"class="form-control" id="againPassword" name="againPassword" value="" required />
									</div>
								</div>
							
						</div>
						<div class="modal-footer">
						       <button type="submit" id="submit_update" class="btn btn-success">修改</button>
							   <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		                </div>
					</div>
					<!-- /.modal-content -->
				</div>
				</form>
				<!-- /.modal-dialog -->
			</div>
		
		
		
		
		<script>
			/*loading frist url*/
			function showView(url,obj){
				var arr = $(obj).parent().children();
				for(var i=0;i<arr.length;i++){
					$(arr[i]).removeClass();
				}
				$(obj).attr("class","act");
				$('iframe').attr('src',url);
				/* $("#iframe").load(function () {
				    var mainheight = $(this).contents().find("#aaaaa").height() + 30;
				    alert(mainheight);
				    $(this).height(mainheight);
				}); */
				/* console.log($("#iframe").contents().find("#aaaaa").css("height")) */
				
					
			}
			/*update iframe size(width and height)*/
		     function changeFrameHeight(){
		        var ifm= document.getElementById("iframe"); 
		        ifm.width = document.documentElement.clientWidth+"px";
		        ifm.height=document.documentElement.clientHeight-58+"px";
		        //alert( ifm.height);
		    }

		    window.onresize=function(){  
		         changeFrameHeight();  
		    };

		</script>
	</body>




</html>