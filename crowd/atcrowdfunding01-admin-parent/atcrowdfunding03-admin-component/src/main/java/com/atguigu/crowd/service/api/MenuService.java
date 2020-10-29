package com.atguigu.crowd.service.api;

import java.util.List;

import com.atguigu.crowd.entity.Menu;

public interface MenuService {

	List<Menu> getAll();

	void addTreeNode(Menu menu);

	void updateTreeNode(Menu menu);

	void deleteTreeNodeById(Menu menu);
}
