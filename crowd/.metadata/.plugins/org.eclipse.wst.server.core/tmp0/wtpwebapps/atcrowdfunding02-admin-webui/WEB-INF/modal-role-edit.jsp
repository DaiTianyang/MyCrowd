
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="updateModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">尚筹网系统弹框</h4>
			</div>
			<div class="modal-body">
				<form class="form-signin" role="form">
					<div class="form-group has-success has-feedback">
						<input 
							name="roleName" type="text"
							class="form-control" placeholder="请输入角色名称"
							autofocus>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button id="closeRoleBtn" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button id="updateRoleBtn" type="button" class="btn btn-success">更新</button>
			</div>
		</div>
	</div>
</div>
