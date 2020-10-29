package com.atguigu.crowd.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.crowd.api.MySQLRemoteService;
import com.atguigu.crowd.entity.vo.PortalTypeVO;
import com.atguigu.crowd.mvc.constant.CrowdConstant;
import com.atguigu.crowd.util.ResultEntity;

@Controller
public class PortalHandler {
	
	@Autowired
	private MySQLRemoteService mySQLRemoteService;
	
	/**
	 * 显示首页，并通过调用远程接口用来查询出数据库的数据---PortalTypeProjectVO
	 * 如果返回数据有效
	 * 则将返回的数据存入modelMap中
	 * @return
	 */
	@RequestMapping("/")
	public String showProtalPage(ModelMap modelMap){
		
		//1.通过调用远程接口来查询出数据
		ResultEntity<List<PortalTypeVO>> resultEntity = mySQLRemoteService.getPortalTypeProjectDataRemote();
		
		//2.判断数据是否有效
		String result = resultEntity.getOperationResult();
		
		//3.如果查询结果有效
		if(ResultEntity.SUCCESS.equals(result)){
			
			//4.返回查询出的
			List<PortalTypeVO> portalTypeVOList = resultEntity.getQueryData();
			
			//5.将查询出的数据存入模型
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_DATA, portalTypeVOList);
		}
		return "portal";
	}

}
