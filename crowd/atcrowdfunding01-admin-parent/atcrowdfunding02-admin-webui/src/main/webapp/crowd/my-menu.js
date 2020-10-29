//生成树
function genereateTree(){
	//1.准备生成树形结构的数据，数据的来源是发送Ajax请求得到
	$.ajax({
		"url":"menu/get/whole/tree.json",
		"type":"post",
		"dataType":"json",
		"success":function(response){
			var result = response.operationResult;
			if(result == "SUCCESS"){
				//2.创建一个JSON对象用来存储对ztree的设置
				var setting = {
						"view":{
							"addDiyDom":myAddDiyDom,
							"addHoverDom":myAddHoverDom,
							"removeHoverDom":myRemoveHoverDom
							
						},
						"data":{
							"key":{
								"url":"maomi"
							}
						}
				};
				var zNodes = response.queryData;
				$.fn.zTree.init($("#treeDemo"),setting,zNodes);
			}
			if(result == "FAILED"){
				layer.msg(response.message);
			}
		},
		"error":function(response){
			layer.msg(response.status+"  "+response.statusText);
		}
	});
}

//更换图标
function myAddDiyDom(treeId, treeNode) {
	// treeId是整个树形结构ul的id
	console.log("treeId:" + treeId);
	// treeNode是当前树形节点的全部数据，包括从后台查询到的Menu的全部属性
	console.log(treeNode);
	var spanId = treeNode.tId + "_ico";
	// zTree 生成 id 的规则
	// 例子：treeDemo_7_ico
	// 解析：ul 标签的 id_当前节点的序号_功能
	// 提示：“ul 标签的 id_当前节点的序号”部分可以通过访问 treeNode 的 tId 属性得到
	// 根据 id 的生成规则拼接出来 span 标签的 id
	// 根据控制图标的span标签的id来找到这个span标签
	// 删除旧的class
	// 添加新的class
	$("#"+spanId)
		.removeClass()
		.addClass(treeNode.icon);
}
//鼠标悬浮添加按钮
function myAddHoverDom(treeId, treeNode){
	// 按钮组的标签结构：<span><a><i></i></a><a><i></i></a></span> 
	// 按钮组出现的位置：节点中 treeDemo_n_a 超链接的后面
	// 为了在需要移除按钮组的时候能够精确定位到按钮组所在 span，需要给 span 设置有 规律的 id
	//<span>的Id
	var btnGroupId = treeNode.tId + "_btnGrp";
	
	if($("#"+btnGroupId).length > 0){
		
		return ;
	}
	
	var addBtn = "<a id='"+treeNode.id+"' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg'></i></a>"; 
	var removeBtn = "<a id='"+treeNode.id+"' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title=' 删 除 节 点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg'></i></a>"; 
	var editBtn = "<a id='"+treeNode.id+"' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title=' 修 改 节 点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg'></i></a>";
	//判断当前级别
	var level = treeNode.level;
	
	var btnHTML = "";
	if(level == 0){
		btnHTML = addBtn;
	}
	if(level == 1){
		btnHTML = addBtn + " "+ editBtn;
		//获取当前子节点数量，如果没有子节点可以显示删除按钮
		var length = treeNode.children.length;
		if(length == 0){
			btnHTML = btnHTML +" "+ removeBtn;
		}
	}
	if(level == 2){
		btnHTML = editBtn + removeBtn;
	}
	//<a>的Id
	var anchorId = treeNode.tId + "_a";
	
	$("#"+anchorId).after("<span id='"+btnGroupId+"'>"+btnHTML+"<span/>");
	
}

//鼠标离开关闭按钮
function myRemoveHoverDom(treeId, treeNode){
	//<span>的Id
	var btnGroupId = treeNode.tId + "_btnGrp";
	$("#"+btnGroupId).remove();
}