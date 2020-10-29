package com.atguigu.crowd.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crowd.entity.po.MemberConfirmInfoPO;
import com.atguigu.crowd.entity.po.MemberLaunchInfoPO;
import com.atguigu.crowd.entity.po.ProjectPO;
import com.atguigu.crowd.entity.po.ReturnPO;
import com.atguigu.crowd.entity.vo.DetailProjectVO;
import com.atguigu.crowd.entity.vo.MemberConfirmInfoVO;
import com.atguigu.crowd.entity.vo.MemberLauchInfoVO;
import com.atguigu.crowd.entity.vo.PortalTypeVO;
import com.atguigu.crowd.entity.vo.ProjectVO;
import com.atguigu.crowd.entity.vo.ReturnVO;
import com.atguigu.crowd.mapper.MemberConfirmInfoPOMapper;
import com.atguigu.crowd.mapper.MemberLaunchInfoPOMapper;
import com.atguigu.crowd.mapper.ProjectItemPicPOMapper;
import com.atguigu.crowd.mapper.ProjectPOMapper;
import com.atguigu.crowd.mapper.ReturnPOMapper;
import com.atguigu.crowd.service.ProjectService;

@Transactional(readOnly = true)
@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectPOMapper projectPOMapper;

	@Autowired
	private ProjectItemPicPOMapper projectItemPicPOMapper;
	
	@Autowired
	private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;
	
	@Autowired
	private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;
	
	@Autowired
	private ReturnPOMapper returnPOMapper;
	
	/**
	 * 将ProjectVO数据保存到数据库并获取他的自增长id
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	@Override
	public void saveProject(ProjectVO projectVO, Integer memberId) {
		
		//一、保存ProjectPO对象
		//1.创建一个新的ProjectPO对象
		ProjectPO projectPO = new ProjectPO();
		
		//2.把之前保存好数据的projectVO里面的数据复制给projectPO
		BeanUtils.copyProperties(projectVO, projectPO);
		
		//修复bug:将memberId添加进projectPO
		projectPO.setMemberid(memberId);
		
		//修复bug:生成创建时间
		String createdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		projectPO.setCreatedate(createdate);
		
		//修复bug:将setStatus设置成0表示即将开始
		projectPO.setStatus(0);
		
		//3.将projectPO对象保存到数据库
		//在ProjectVOMapper.xml中加入，以便后来方便获取自增长的id的值
		//<insert id="insertSelective" useGeneratedKeys="true" keyProperty="id"
		projectPOMapper.insertSelective(projectPO);
		
		//4.从projectPO中获取到自增的主键值
		Integer projectId = projectPO.getId();
		
		//二、保存项目、分类关联关系信息----- private List<Integer> typeIdList;
		//1.从projectVO对象中获取到typeIdList
		List<Integer> typeIdList = projectVO.getTypeIdList();
		projectPOMapper.insertTypeRelationship(typeIdList,projectId);
		
		
		//三、保存项目、标签关联信息-------- private List<Integer> tagIdList;
		List<Integer> tagIdList = projectVO.getTagIdList();
		projectPOMapper.insertTagRelationship(tagIdList,projectId);
		
		
		//四、保存项目中详情图片信息-------- private List<String> detailPicturePathList;
		List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
		projectItemPicPOMapper.insertPathList(detailPicturePathList,projectId);
		
		
		//五、保存项目发起人信息----------- private MemberLauchInfoVO memberLauchInfoVO;
		MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
		MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
		BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
		memberLaunchInfoPO.setMemberid(memberId);
		memberLaunchInfoPOMapper.insertSelective(memberLaunchInfoPO);
		
		
		//六、保存项目汇报信息------------ private List<ReturnVO> returnVOList;
		List<ReturnVO> returnVOList = projectVO.getReturnVOList();
		
		List<ReturnPO> returnPOList = new ArrayList();
		
		for (ReturnVO returnVO : returnVOList) {
			
			ReturnPO returnPO = new ReturnPO();
			
			BeanUtils.copyProperties(returnVO, returnPO);
			
			returnPOList.add(returnPO);
		}
		returnPOMapper.insertReturnPOBatch(returnPOList,projectId);
		
		
		//七、保存项目的确认信息 ---------- private MemberConfirmInfoVO memberConfirmInfoVO;
		MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
		MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
		BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPO);
		memberConfirmInfoPO.setMemberid(memberId);
		memberConfirmInfoPOMapper.insertSelective(memberConfirmInfoPO);
	}

	@Override
	public List<PortalTypeVO> getPortalTypeVO() {
		
		return projectPOMapper.selectPortalTypeVOList();
	}

	@Override
	public DetailProjectVO getDetailProjectVOById(Integer projectId) {
		//获取到DetailProjectVO对象
		DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(projectId);
		//根据status来确定StatusText
		Integer status = detailProjectVO.getStatus();
		switch (status) {
		case 0:
			detailProjectVO.setStatusText("审核中");
			break;
		case 1:
			detailProjectVO.setStatusText("众筹中");
			break;
		case 2:
			detailProjectVO.setStatusText("审核成功");
			break;
		case 3:
			detailProjectVO.setStatusText("众筹已关闭");
			break;

		default:
			break;
		}
		//根据deployDate计算lastDate
		//2020-01-01
		String deployDate = detailProjectVO.getDeployDate();
		
		//获取到当前日期
		Date currentDay = new Date();
		
		//把众筹日期解析成Date类型
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date deployDay = simpleDateFormat.parse(deployDate);
			//获取当前日期的时间戳
			long currenTime = currentDay.getTime();
			
			//获取众筹日期的时间戳
			long deployTime = deployDay.getTime();
			
			//两个时间戳相减计算已经过去的时间
			//	秒	  分钟      小时    天
			// /1000 /60 /60 /24
			long paseDays = (currenTime-deployTime)/1000/60/60/24;
			
			//获取总的众筹天数
			Integer totalDays = detailProjectVO.getDay();
			
			//使用总的众筹天数减去过去的时间就是剩余的众筹天熟
			Integer lastDay = (int) (totalDays-paseDays);
			
			//将得出的剩余天数赋值给detailProjectVO对象的LastDay属性
			detailProjectVO.setLastDay(lastDay);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return detailProjectVO;
	}
	
	
}
