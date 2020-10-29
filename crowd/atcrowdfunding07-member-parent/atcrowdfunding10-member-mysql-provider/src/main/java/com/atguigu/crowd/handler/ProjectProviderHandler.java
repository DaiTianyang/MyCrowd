package com.atguigu.crowd.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.crowd.entity.vo.DetailProjectVO;
import com.atguigu.crowd.entity.vo.PortalTypeVO;
import com.atguigu.crowd.entity.vo.ProjectVO;
import com.atguigu.crowd.service.ProjectService;
import com.atguigu.crowd.util.ResultEntity;


@RestController
public class ProjectProviderHandler {

	@Autowired
	private ProjectService projectService;
	
	/**
	 * 通过首页的众筹项目展示，通过点击传入对应项目的projectId，并调用该方法进行对应众筹项目的详细信息查询并返回json数据
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/get/project/detail/remote/{projectId}")
	public ResultEntity<DetailProjectVO> getDetailProjectVODataRemote(@PathVariable("projectId") Integer projectId){
		try {
			DetailProjectVO detailProjectVOById = projectService.getDetailProjectVOById(projectId);
			return ResultEntity.successWithData(detailProjectVOById);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}
	
	
	/**
	 * 调用方法查询前台列表显示的数据 查询出PortalTypeVO并返回json数据
	 * @return
	 */
	@RequestMapping("/get/portal/type/project/data/remote")
	public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote(){
		try {
			List<PortalTypeVO> portalTypeVOList = projectService.getPortalTypeVO();
			return ResultEntity.successWithData(portalTypeVOList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}
	
	/**
	 * 保存我发起的众筹项目的handler
	 * @param projectVO
	 * @param memberId
	 * @return
	 */
	@RequestMapping("/save/project/vo/remote")
	public ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO,@RequestParam("memberId") Integer memberId){
		
		try {
			projectService.saveProject(projectVO,memberId);
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}
}
