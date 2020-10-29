<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-menu.js"></script>
<script type="text/javascript">

	$(function(){
		//直接调用生成树的函数
		genereateTree();
		//添加
		$("#treeDemo").on("click",".addBtn",function(){
			//获取当前节点的ID
			window.treeId = this.id;
			//打开模态框
			$("#addModal").modal("show");
			return false;
		});
		$("#addMenuBtn").click(function(){
			//获取添加节点的name
			var name = $.trim($("#addModal [name=name]").val());
			//获取地址
			var url = $.trim($("#addModal [name=url]").val());
			//获取选中的图标
			var icon = $("#addModal [name=icon]:checked").val();
			if(name == "" || icon == null){
				layer.msg("请输入完整信息再进行相关操作");
				return ;
			}
			$.ajax({
				"url":"menu/add.json",
				"type":"post",
				"data":{
					name:name,
					url:url,
					icon:icon,
					pid:treeId
				},
				"dataType":"json",
				"success":function(response){
					var result = response.operationResult;
					if(result == "SUCCESS"){
						layer.msg("操作成功！");
						//刷新树形
						genereateTree();
					}
					if(result == "FAILED"){
						layer.msg("操作失败！"+response.message);
					}
				},
				"error":function(response){
					layer.msg(response.status+"  "+response.statusText);
				}
			});
			//关闭模态框
			$("#addModal").modal("hide");
			//清空表单
			$("#resetMenuBtn").click();
		});
		//更新信息的回显
		$("#treeDemo").on("click",".editBtn",function(){
			//获取当前节点的ID
			window.treeId = this.id;
			//打开模态框
			$("#editModal").modal("show");
			
			var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
			
			var key = "id";
			
			var value = window.treeId;
			
			var currentNode = zTreeObj.getNodeByParam(key,value);
			
			//回显数据表单
			$("#editModal [name=name]").val(currentNode.name);
			$("#editModal [name=url]").val(currentNode.url);
			//radio回显的本质就是把value放到数组里面
			$("#editModal [name=icon]").val([currentNode.icon]);
			return false;
		});
		//更新
		$("#editMenuBtn").click(function(){
			//获取添加节点的name
			var name = $.trim($("#editModal [name=name]").val());
			//获取地址
			var url = $.trim($("#editModal [name=url]").val());
			//获取选中的图标
			var icon = $("#editModal [name=icon]:checked").val();
			if(name == "" || icon == null){
				layer.msg("请输入完整信息再进行相关操作");
				return ;
			}
			$.ajax({
				"url":"menu/edit.json",
				"type":"post",
				"data":{
					name:name,
					url:url,
					icon:icon,
					id:window.treeId
				},
				"dataType":"json",
				"success":function(response){
					var result = response.operationResult;
					if(result == "SUCCESS"){
						layer.msg("操作成功！");
						//刷新树形
						genereateTree();
					}
					if(result == "FAILED"){
						layer.msg("操作失败！"+response.message);
					}
				},
				"error":function(response){
					layer.msg(response.status+"  "+response.statusText);
				}
			});
			//关闭模态框
			$("#editModal").modal("hide");
		});
		//删除打开模态框，信息回显，提示确认是否删除
		$("#treeDemo").on("click",".removeBtn",function(){
			$("#removeNodeSpan").empty();
			//获取当前节点的ID
			window.treeId = this.id;
			var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var key = "id";
			var value = window.treeId;
			var currentNode = zTreeObj.getNodeByParam(key,value);
			//回显数据表单
			//$("#deleteModal [name=removeNodeSpan]").val(currentNode.name);
			$("#removeNodeSpan").append(currentNode.name);
			//打开模态框
			$("#deleteModal").modal("show");
			return false;
		});
		//删除
		$("#deleteMenuBtn").click(function(){
			
			$.ajax({
				"url":"menu/delete.json",
				"type":"post",
				"data":{
					id:window.treeId
				},
				"dataType":"json",
				"success":function(response){
					var result = response.operationResult;
					if(result == "SUCCESS"){
						layer.msg("操作成功！");
						//刷新树形
						genereateTree();
					}
					if(result == "FAILED"){
						layer.msg("操作失败！"+response.message);
					}
				},
				"error":function(response){
					layer.msg(response.status+"  "+response.statusText);
				}
			});
			//关闭模态框
			$("#deleteModal").modal("hide");
			
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
						<i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
						<div style="float: right; cursor: pointer;" data-toggle="modal"
							data-target="#myModal">
							<i class="glyphicon glyphicon-question-sign"></i>
						</div>
					</div>
					<div class="panel-body">
						<ul id="treeDemo" class="ztree">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<%@include file="/WEB-INF/modal-menu-add.jsp"%>
<%@include file="/WEB-INF/modal-menu-edit.jsp"%>
<%@include file="/WEB-INF/modal-menu-confirm.jsp"%>
</html>
