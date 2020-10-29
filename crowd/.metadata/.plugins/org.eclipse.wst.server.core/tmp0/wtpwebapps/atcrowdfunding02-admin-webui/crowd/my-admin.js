/**
 * 删除Admin的模态框
 * 
 */
function showDeleteModal(adminlist){
	
	$("#deleteModal").modal("show");
	$("#roleNameDiv").empty();
	//$("#deleteModal [id=roleNameDiv]").val("");	
	window.roleIdList = [];
	for(var i = 0; i<adminlist.length; i++){
		var admin = adminlist[i];
		var id = admin.id;
		$("#roleNameDiv").append(id+"<br/>");
		window.roleIdList.push(id);
	}
	/*for(var i = 0; i<roleIdList.length; i++){
		alert(roleIdList[i]);
	}*/
	
}
