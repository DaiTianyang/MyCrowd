<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<link rel="stylesheet" href="ztree/zTreeStyle.css" />
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-role.js"></script>
<script type="text/javascript">
	$(function() {

		//1.为分页操作初始化数据
		window.pageNum = 1;
		window.pageSize = 10;
		window.keyword = "";
		//2.调用执行分页的函数，执行分页效果
		generatePage();
		//3.为搜索按钮绑定单击函数
		$("#searchBtn").click(function() {

			//1.获取keyword
			window.keyword = $("#keywordInput").val();
			window.pageNum = 1;
			//调用分页函数
			generatePage();
		});
		//打开添加模态框
		$("#addModalBtn").click(function() {

			//$("#addModal").empty();
			$("#addModal").modal("show");

		});
		//添加新角色
		$("#saveRoleBtn")
				.click(
						function() {

							//获取到input框内的roleName
							//#addModal [name = roleName]  表示在addModal模态框下的name=roleName元素
							//$.trim,去掉前后空格
							var roleName = $
									.trim($("#addModal [name=roleName]").val());
							if (roleName == "") {
								layer.msg("输入角色名称不能为空！");

							}
							//发送ajax请求
							if (roleName != "") {
								$.ajax({
									"url" : "role/save.json",
									"type" : "post",
									"data" : {
										"name" : roleName
									},
									"dataType" : "json",
									"success" : function(response) {
										var result = response.operationResult;
										if (result == "SUCCESS") {
											layer.msg("操作成功！");

											//重新加载分页
											window.pageNum = 999999;
											generatePage();
										}

										if (result == "FAILED") {
											layer.msg("操作失败！"
													+ response.message);
										}
									},
									"error" : function(response) {
										layer.msg(response.status + "  "
												+ response.statusText);
									}

								});
							}

							$("#addModal").modal("hide");

							//清理模态框
							$("#addModal [name = roleName]").val("");
							//重新加载分页
							/* window.pageNum = 999999;
							generatePage(); */
						});
		//回显信息
		$("#rolePageBody").on("click", ".pencilBtn", function() {
			//打开模态框
			$("#updateModal").modal("show");

			//获取到角色名称
			var roleName = $(this).parent().prev().text();

			//获取当前角色的id
			//为了让执行更新的按钮能够获取到roleId所以将其设置为全局变量
			window.roleId = this.id;

			$("#updateModal [name=roleName]").val(roleName);
		});
		//更新
		$("#updateRoleBtn").click(
				function() {
					//取出文本框内的角色信息
					var roleName = $("#updateModal [name=roleName]").val();
					if (roleName == "") {
						layer.msg("输入角色名称不能为空！");
					}
					if (roleName != "") {
						$.ajax({
							"url" : "role/update.json",
							"type" : "post",
							"data" : {
								"id" : window.roleId,
								"name" : roleName
							},
							"dataType" : "json",
							"success" : function(response) {
								var result = response.operationResult;
								if (result == "SUCCESS") {
									layer.msg("操作成功！");

									//重新加载分页
									generatePage();
									$("#updateModal").modal("hide");
								}
								if (result == "FAILED") {
									layer.msg("操作失败！" + response.message);
								}
							},
							"error" : function(response) {
								layer.msg(response.status + "  "
										+ response.statusText);
								$("#updateModal").modal("hide");
							}
						});
					}
				});
		//打开删除模态框，提示是否确认删除,点击确认进行删除
		$("#removeRoleBtn").click(function() {
			var requestBody = JSON.stringify(window.roleIdList);
			$.ajax({
				"url" : "role/delete/by/id/list.json",
				"type" : "post",
				"data" : requestBody,
				"contentType" : "application/json;charset=UTF-8",
				"dataType" : "json",
				"success" : function(response) {
					var result = response.operationResult;
					if (result == "SUCCESS") {
						layer.msg("删除成功！");
						// 重新加载分页
						generatePage();
						$("#updateModal").modal("hide");
					}
					if (result == "FAILED") {
						layer.msg("删除失败！" + response.message);
					}
				},
				"error" : function(response) {
					layer.msg(response.status + "  " + response.statusText);
					$("#updateModal").modal("hide");
				}
			});

			//关闭模态框
			$("#deleteModal").modal("hide");
		});
		//单条删除
		$("#rolePageBody").on("click", ".removeBtn", function() {

			//获取到角色名称
			var roleName = $(this).parent().prev().text();
			var rolelist = [ {
				roleId : this.id,
				roleName : roleName
			} ];
			showDeleteModal(rolelist);
		});

		//给总的checkbox绑定单击响应函数
		$("#summaryBox").click(function() {
			//获取自身选择状态
			var currentStatus = this.checked;
			//用当前多选框的状态赋值给其他多选框
			$(".itembox").prop("checked", currentStatus);

		});
		//反向操作
		$("#rolePageBody").on("click", ".itembox", function() {
			//获取选中的全部itembox的数量
			var checkedBoxCount = $(".itembox:checked").length;
			//获取itemBox的全部数量
			var totalBoxCount = $(".itembox").length;

			$("#summaryBox").prop("checked", checkedBoxCount == totalBoxCount);
		});

		//给批量删除按钮绑定单击响应函数
		$("#deleteRoleBtn").click(function() {
			//创建数组用来存放role对象
			var rolelist = [];

			$(".itembox:checked").each(function() {

				var roleId = this.id;
				var roleName = $(this).parent().next().text();
				rolelist.push({
					roleId : this.id,
					roleName : roleName
				});
			});

			if (rolelist.length == 0) {

				layer.msg("请至少选择一个角色名称再进行操作！");
				return;
			}
			showDeleteModal(rolelist);
		});
		//给分配权限按钮绑定单级响应函数打开模态框
		$("#rolePageBody").on("click", ".checkBtn", function() {
			//alert("111");
			//把当前角色的id存入全局变量
			window.roleId = this.id;
			//打开模态框
			$("#assignModal").modal("show");

			fillAuthTree();
		});
		$("#assignBtn").click(function() {

			//1.收集树形结构中被勾选的节点
			//声明一个专用数组来保存被勾选的id
			var authIdArray = [];
			//2.获取zTreeObj对象
			var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
			//3.获取全部勾选的节点
			var checkedNodes = zTreeObj.getCheckedNodes();
			//4遍历数组
			for (var i = 0; i < checkedNodes.length; i++) {
				var checkedNode = checkedNodes[i];
				var authId = checkedNode.id;
				authIdArray.push(authId);
			}
			//alert(authIdArray);
			var requestBoby = {
				"authIdArray" : authIdArray,
				//为了方便服务器端handler方法能够统一使用List<Integer>方式接收数据，roleId也存入数组内
				"roleId" : [ window.roleId ]
			};
			requestBoby = JSON.stringify(requestBoby);
			alert(requestBoby);
			$.ajax({
				"url" : "assign/do/role/assign/auth.json",
				"type" : "post",
				"data" : requestBoby,
				"contentType" : "application/json;charset=UTF-8",
				"dataType" : "json",
				"success" : function(response) {
					var result = response.operationResult;
					if (result == "SUCCESS") {
						layer.msg("操作成功！");
					}
					if (result == "FAILED") {
						layer.msg("操作失败！"+response.message);
					}
				},
				"error" : function(response) {
					layer.msg(response.status + "  " + response.statusText);
				}
			});
			$("#assignModal").modal("hide");
		});
	});
</script>
<body>
	<%@include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form method="post" class="form-inline" role="form"
							style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input id="keywordInput" class="form-control has-success"
										type="text" placeholder="请输入查询条件">
								</div>
							</div>
							<button id="searchBtn" type="button" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button id="deleteRoleBtn" type="button" class="btn btn-danger"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button id="addModalBtn" type="button" class="btn btn-primary"
							style="float: right;">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input id="summaryBox" type="checkbox"></th>
										<th>名称</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody id="rolePageBody">
								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<div id="Pagination" class="pagination">
												<!-- 这里显示分页 -->
											</div>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<%@include file="/WEB-INF/modal-role-remove.jsp"%>
<%@include file="/WEB-INF/modal-role-add.jsp"%>
<%@include file="/WEB-INF/modal-role-edit.jsp"%>
<%@include file="/WEB-INF/modal-role-assign-auth.jsp"%>
</html>
