package com.atguigu.crowd.mvc.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crowd.entity.Auth;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.service.api.AuthService;
import com.atguigu.crowd.service.api.RoleService;
import com.atguigu.crowd.util.ResultEntity;

@Controller
public class AssignHandler {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthService authService;
	
	/**
	 * 
	 * @return
	 */
	@PreAuthorize("hasAuthority('admin:role')")
	@RequestMapping("/assign/to/assign/role/page.html")
	public String toAssignRolePage(
			@RequestParam("adminId") Integer adminId,
			ModelMap modelMap
			){
		
		//1.查询已经分配角色
		
		List<Role> assignRoleList = roleService.getAssignedRole(adminId);
		
		//2.查询未分配角色
		List<Role> unAssignRoleList = roleService.getUnAssignedRole(adminId);
		
		//将数据添加到modelmap
		modelMap.addAttribute("assignRoleList", assignRoleList);
		modelMap.addAttribute("unAssignRoleList", unAssignRoleList);
		return "assign-role";
	}
	
	@RequestMapping("/assign/do/role/assign.html")
	public String saveAdminRoleRelationship(
			@RequestParam("adminId") Integer adminId,
			@RequestParam("pageNum") Integer pageNum,
			@RequestParam("keyword") String keyword,
			//我们允许用户在页面上取消所有已经分配的角色在提交表单，
			//设置required=false表示这个请求参数不是必须的
			@RequestParam(value="roleIdList",required=false) List<Integer> roleIdList
			){
		
		adminService.saveAdminRoleRelationship(adminId,roleIdList);
		return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
	}
	
	/**
	 * 查询全部的auth
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/assign/get/all/auth.json")
	public ResultEntity<List<Auth>> getAllAuth(){
		List<Auth> authList = authService.getAll();
		return ResultEntity.successWithData(authList);
	}
	
	/**
	 * 根据roleId查询出其拥有的权限并回显到页面
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
	public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(
			@RequestParam("roleId") Integer roleId){
		List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);
		return ResultEntity.successWithData(authIdList);
	} 
	
	/**
	 * 保存给角色分配auth
	 * @return
	 */
	@PreAuthorize("hasAuthority('auth:role')")
	@ResponseBody
	@RequestMapping("/assign/do/role/assign/auth.json")
	public ResultEntity<String> saveRoleAuthRelathinship(
			@RequestBody Map<String,List<Integer>> map){
		 authService.saveRoleAuthRelathinship(map);
		return ResultEntity.successWithoutData();
	}
}
