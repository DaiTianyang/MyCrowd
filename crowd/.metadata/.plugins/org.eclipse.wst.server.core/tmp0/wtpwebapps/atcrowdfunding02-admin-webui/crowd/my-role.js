/**
 * @author User 声明专门的函数用来在分配Auth的模态框中显示Auth的属性结构数据
 */
function fillAuthTree() {
	// 1.发送ajax请求查询Auth数据
	var ajaxReturn = $.ajax({
		"url" : "assign/get/all/auth.json",
		"type" : "post",
		"dataType" : "json",
		"async" : false,
	});
	if (ajaxReturn.status != 200) {
		layer.msg("请求处理出错！相应状态码是：" + ajaxReturn.status + "说明是："
				+ ajaxReturn.statusText);
		return;
	}
	// 2.从相应结果中获取Auth的json数据
	// 从服务器端查询到的list不需要组装成树形结构，这里我们交给zTree去组装
	var authList = ajaxReturn.responseJSON.queryData;

	// 3.准备对zTree进行设置的json对象
	var setting = {
		"data" : {
			"simpleData" : {
				// 开启简单json功能
				"enable" : true,
				"pIdKey" : "categoryId"
			},
			"key" : {
				"name" : "title"
			}
		},
		"check" : {
			"enable" : true
		}
	};
	// 4.生成树形结构
	// <ul id="authTreeDemo" class="ztree"></ul>
	$.fn.zTree.init($("#authTreeDemo"), setting, authList);
	// 获取zTreeObj对象
	var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
	// 调用zTreeObj对象的方法，把节点展开
	zTreeObj.expandAll(true);
	// 5.查询已分配的Auth的id组成的List
	ajaxReturn = $.ajax({
		"url" : "assign/get/assigned/auth/id/by/role/id.json",
		"type" : "post",
		"data" : {
			"roleId" : window.roleId
		},
		"dataType" : "json",
		"async" : false
	});
	if (ajaxReturn.status != 200) {
		layer.msg("请求处理出错！相应状态码是：" + ajaxReturn.status + "说明是："
				+ ajaxReturn.statusText);
		return;
	}
	// 从响应结果中获取authIdArray
	var authIdArray = ajaxReturn.responseJSON.queryData;
	//alert(authIdArray);
	// 6.根据authIdArray把树形结构中对应的节点勾选
	// 1).遍历数组authIdArray
	for (var i = 0; i < authIdArray.length; i++) {
		var authId = authIdArray[i];
		// 2).根据id查询属性结构中对应的节点
		var treeNode = zTreeObj.getNodeByParam("id", authId);
		// 3).将treeNode设置为被勾选
		var checked = true;// 表示节点勾选
		var checkTypeFlag = false;// 表示不联动
		zTreeObj.checkNode(treeNode, checked, checkTypeFlag);
	}

}

/**
 * 分页总执行函数，生成分页效果
 */
function generatePage() {

	// 1.获取分页数据
	var pageInfo = getPageInfoRemote();
	// 2.填充表格
	fillTableBody(pageInfo);
	$("#summaryBox").prop("checked", false);
}
/**
 * 远程获取PageInfo
 */
function getPageInfoRemote() {

	var ajaxResult = $.ajax({
		"url" : "role/get/page.json",
		"type" : "post",
		"data" : {
			"pageNum" : window.pageNum,
			"pageSize" : window.pageSize,
			"keyword" : window.keyword
		},
		"async" : false,
		"dataType" : "json",
	});
	console.log(ajaxResult);

	// 判断当前相应状态码是否为200
	var statusCode = ajaxResult.status;
	/**
	 * 如果当前状态码不为200，说明发生错误或其他意外情况，显示提示消息，让当前函数停止运行
	 */
	if (statusCode != 200) {
		layer.msg("失败！相应状态码=" + statusCode + "，说明信息=" + ajaxResult.statusText);
		return null;
	}
	/**
	 * 如果相应状态码为200，说明请求处理成功，获取pageInfo
	 */
	var resultEntity = ajaxResult.responseJSON;

	// 从resultEntity中获取result属性
	var result = resultEntity.result;

	// 判断result是否成功
	if (result == "FAILED") {
		layer.msg(resultEntity.message);
		return null;
	}

	// 获取pageInfo
	var pageInfo = resultEntity.queryData;
	// 返回pageInfo
	return pageInfo;
}
/**
 * 将数据填充到Table
 */
function fillTableBody(pageInfo) {

	// 清空旧数据
	$("#rolePageBody").empty();
	// 清空页码导航条，为了让搜索没有数据时不显示页码导航条
	$("#Pagination").empty();

	// 判断pageInfo对象是否有效
	if (pageInfo == null || pageInfo == undefined || pageInfo.list == null
			|| pageInfo.list.length == 0) {
		$("#rolePageBody").append(
				"<tr><td style='text-align: center;' colspan='4'>抱歉！没有查询到您搜索的数据</td></tr>");
		return;
	}
	// 使用pageInfo填充TableBody
	for (var i = 0; i < pageInfo.list.length; i++) {

		var role = pageInfo.list[i];

		var roleId = role.id;

		var roleName = role.name;

		var numberTd = "<td>" + (i + 1) + "</td>";
		var checkboxTd = "<td><input id='" + roleId
				+ "' class='itembox' type='checkbox'></td>"
		var roleNameTd = "<td>" + roleName + "</td>"
		var checkBtn = "<button id='"
				+ roleId
				+ "' type='button' class='btn btn-success btn-xs checkBtn'><i class='glyphicon glyphicon-check'></i></button>";
		var pencilBtn = "<button id='"
				+ roleId
				+ "' type='button' class='btn btn-primary btn-xs pencilBtn'><i class='glyphicon glyphicon-pencil'></i></button>";
		var removeBtn = "<button id='"
				+ roleId
				+ "' type='button' class='btn btn-danger btn-xs removeBtn'><i class='glyphicon glyphicon-remove'></i></button>";
		var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn
				+ "</td>"

		var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd
				+ "</tr>"

		$("#rolePageBody").append(tr);
	}

	// 生成分页导航条
	generateNavigator(pageInfo);
}
/**
 * 分页导航栏，
 */
function generateNavigator(pageInfo) {

	// 1.获取总记录数
	var totalRecord = pageInfo.total;

	// 2.配置其他属性
	var properties = {

		"num_edge_entries" : 3,
		"num_display_entries" : 5,
		"callback" : paginationCallBack,
		"items_per_page" : pageInfo.pageSize,
		"current_page" : pageInfo.pageNum - 1,
		"prev_text" : "上一页",
		"next_text" : "下一页"
	}

	$("#Pagination").pagination(totalRecord, properties);
}
/**
 * 执行翻页的回调函数
 */
function paginationCallBack(pageIndex, jQuery) {

	// 1.修改全局变量的pageNum
	window.pageNum = pageIndex + 1;

	// 2.调用分页函数
	generatePage();

	// 3.取消页码超链接的默认行为
	return false;
}

/**
 * 删除Role的模态框
 * 
 */
function showDeleteModal(rolelist) {

	$("#deleteModal").modal("show");
	$("#roleNameDiv").empty();
	// $("#deleteModal [id=roleNameDiv]").val("");
	window.roleIdList = [];
	for (var i = 0; i < rolelist.length; i++) {
		var role = rolelist[i];
		var roleName = role.roleName;
		$("#roleNameDiv").append(roleName + "<br/>");
		var roleId = role.roleId;
		window.roleIdList.push(roleId);
	}

}
