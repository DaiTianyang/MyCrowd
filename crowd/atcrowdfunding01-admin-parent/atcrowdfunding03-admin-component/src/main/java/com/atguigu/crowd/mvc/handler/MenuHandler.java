package com.atguigu.crowd.mvc.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crowd.entity.Menu;
import com.atguigu.crowd.service.api.MenuService;
import com.atguigu.crowd.util.ResultEntity;

@Controller
public class MenuHandler {

	@Autowired
	private MenuService menuService;
	
	@ResponseBody
	@RequestMapping("/menu/get/whole/tree.json")
	public ResultEntity<Menu> getWholeTreeNew(){
		
		//1.查询全部menu对象
		List<Menu> menuList = menuService.getAll();
		
		//2.声明一个menu对象来储存根节点
		Menu root  = null;
		
		//3.创建一个map对象用来储存id和Menu对象的对应关系便于查找父节点
		Map<Integer,Menu> menuMap = new HashMap<>();
		
		//4.遍历Menu List填充Menu MAp
		for (Menu menu:menuList) {
			
			Integer id = menu.getId();
			
			menuMap.put(id, menu);
		}
		
		//5.再次遍历menuList查找根节点、组装父子节点
		for (Menu menu:menuList) {
			
			Integer pid = menu.getPid();
			//判断是否为根节点
			if (pid == null) {
				
				root = menu;
				
				continue;
				
			}
			
			//6.如果Pid不为null,说明当前节点有父节点，那么可以根据pid到menuMap对象中查找对应的menu对象
			
			Menu father = menuMap.get(pid);
			
			//7.将当前 父节点存入集合children
			father.getChildren().add(menu);
			
		}
		return ResultEntity.successWithData(root);
	}
	
	@ResponseBody
	@RequestMapping("/menu/add.json")
	public ResultEntity<String> addTreeNode(Menu menu){
		menuService.addTreeNode(menu);
		return ResultEntity.successWithoutData();
	}
	
	@ResponseBody
	@RequestMapping("menu/edit.json")
	public ResultEntity<String> editTreeNode(Menu menu){
		menuService.updateTreeNode(menu);
		return ResultEntity.successWithoutData();
	}
	
	@ResponseBody
	@RequestMapping("/menu/delete.json")
	public ResultEntity<String> deleteTreeNode(Menu menu){
		menuService.deleteTreeNodeById(menu);
		return ResultEntity.successWithoutData();
	}
}
