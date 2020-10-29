package com.atguigu.crowd.mvc.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.mvc.constant.CrowdConstant;
import com.atguigu.crowd.service.api.RoleService;
import com.atguigu.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;

@Controller
public class RoleHandler {

	@Autowired
	private RoleService roleService;
	
	@PreAuthorize("hasRole('部长') OR hasAuthority('role:get')")
	@ResponseBody
	@RequestMapping("/role/get/page.json")
	public ResultEntity<PageInfo<Role>> getPageInfo(
				@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
				@RequestParam(value="pageSize",defaultValue="5") Integer pageSize,
				@RequestParam(value="keyword",defaultValue="") String keyword
				//ModelMap modelMap
			){
		PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
		
		/*for (int i = 0; i < pageInfo.getSize(); i++) {
			System.out.println(pageInfo.getList().get(i));
		}*/
		//modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
		
		return ResultEntity.successWithData(pageInfo);
	}
	
	@PreAuthorize("hasAuthority('role:add')")
	@ResponseBody
	@RequestMapping("/role/save.json")
	public ResultEntity<String> saveRole(Role role){
		roleService.save(role);
		return ResultEntity.successWithoutData();
	}
	
	@PreAuthorize("hasAuthority('role:update')")
	@ResponseBody
	@RequestMapping("/role/update.json")
	public ResultEntity<String> updateRole(Role role){
		
		roleService.update(role);
		return ResultEntity.successWithoutData();
	}
	
	@PreAuthorize("hasAuthority('role:delete')")
	@ResponseBody
	@RequestMapping("/role/delete/by/id/list.json")
	public ResultEntity<String> deleteRole(@RequestBody List<Integer> rolelist){
		
		//System.out.println("******************************************"+rolelist.get(0));
		roleService.delete(rolelist);
		return ResultEntity.successWithoutData();
	}
}
