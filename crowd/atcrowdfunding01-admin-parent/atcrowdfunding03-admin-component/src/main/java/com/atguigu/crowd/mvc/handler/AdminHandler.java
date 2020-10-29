package com.atguigu.crowd.mvc.handler;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.mvc.constant.CrowdConstant;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;

@Controller
public class AdminHandler {

	@Autowired
	private AdminService adminService;

	/**
	 * 退出登录
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/admin/do/logout.html")
	public String doLogout(HttpSession session) {

		// 强制session失效
		session.invalidate();
		return "redirect:/admin/to/login/page.html";
	}

	/**
	 * 
	 * 后台账号登录
	 * 
	 * @param loginAcct
	 * @param userPswd
	 * @param session
	 * @return
	 */
	@RequestMapping("/admin/do/login.html")
	public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd,
			HttpSession session) {
		// 调用service方法执行登录检查

		// 方法如果返回Admin对象则登录成功，如果账号密码不正确则抛出异常
		Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);

		// 将登录成功返回的Admin对象存入session

		session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
		return "redirect:/admin/to/main/page.html";
	}

	/**
	 * 
	 * 用户维护功能查询，实现分页和条件查询分页
	 * 
	 * @param keyword
	 * @param pageNum
	 * @param pageSize
	 * @param modelMap
	 * @return
	 */
	//@PostAuthorize("returnObject.data.loginAcct==principal.username")
	@RequestMapping("/admin/get/page.html")
	public String getPageInfo(
			// 传入参数，默认为空
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			// 分页页码默认为1
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			// pageSize默认值为5
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, ModelMap modelMap) {

		// 调用service方法获取PageInfo
		PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

		// 将PageInfo对象存入模型
		modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);

		return "admin-page";
	}

	/**
	 * 删除单条用户数据
	 * @param adminId
	 * @param pageNum
	 * @param keyword
	 * @return
	 */
	@PreAuthorize("hasAuthority('user:delete')")
	@RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
	public String remove(
			@PathVariable("adminId") Integer adminId, 
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("keyword") String keyword) {
		adminService.removeAdmin(adminId);
		return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword + "";
	}
	
	/**
	 * 添加新用户
	 * 设置用户必须有user:save的权限才能进行保存
	 * @param admin
	 * @return
	 */
	@PreAuthorize("hasAuthority('user:save')")
	@RequestMapping("/admin/save.html")
	public String add(Admin admin){
		adminService.saveAdmin(admin);
		return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
	}
	
	
	/**
	 * 跳转到admin-edit页面并实现数据回写
	 * @param adminId
	 * @param modelMap
	 * @return
	 */
	@PreAuthorize("hasAuthority('user:update')")
	@RequestMapping("/admin/to/edit/page.html")
	public String toEditPage(
			@RequestParam("adminId") Integer adminId,
			ModelMap modelMap
			){
	
		Admin admin = adminService.getAdminById(adminId);
		modelMap.addAttribute("admin", admin);
		return "admin-edit";
		
	}
	
	
	@RequestMapping("/admin/update.html")
	public String update(
			Admin admin,
			@RequestParam("pageNum") Integer pageNum,
			@RequestParam("keyword") String keyword
			){
		
		adminService.updateAdmin(admin);
		return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword + "";
	}
	
	@PreAuthorize("hasAuthority('user:delete')")
	@ResponseBody
	@RequestMapping("/admin/delete/by/id/list.json")
	public ResultEntity<String> deleteAll(@RequestBody List<Integer> rolelist){
		
		adminService.deleteAll(rolelist);
		return ResultEntity.successWithoutData();
	}
	
}
