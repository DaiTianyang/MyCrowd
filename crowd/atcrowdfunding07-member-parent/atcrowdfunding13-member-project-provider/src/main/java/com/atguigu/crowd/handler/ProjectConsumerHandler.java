package com.atguigu.crowd.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.crowd.api.MySQLRemoteService;
import com.atguigu.crowd.config.OSSProperties;
import com.atguigu.crowd.entity.vo.DetailProjectVO;
import com.atguigu.crowd.entity.vo.MemberConfirmInfoVO;
import com.atguigu.crowd.entity.vo.MemberLoginVO;
import com.atguigu.crowd.entity.vo.ProjectVO;
import com.atguigu.crowd.entity.vo.ReturnVO;
import com.atguigu.crowd.mvc.constant.CrowdConstant;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;

@Controller
public class ProjectConsumerHandler {

	@Autowired
	private OSSProperties ossProperties;
	
	@Autowired
	private MySQLRemoteService mySQLRemoteService;
	
	
	/**
	 * 通过在前台主页面点击众筹项目，并传入该项目的projectId
	 * 调用远程接口，通过projectId查询出对应众筹项目的详细信息并返回project-detail页面进行显示
	 * @param projectId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/get/project/detail/{projectId}")
	public String getProjectDetail(
			@PathVariable("projectId") Integer projectId,
			ModelMap modelMap){
		ResultEntity<DetailProjectVO> resultEntity = mySQLRemoteService.getDetailProjectVODataRemote(projectId);
		if(ResultEntity.SUCCESS.equals(resultEntity.getOperationResult())){
			DetailProjectVO detailProjectVO = resultEntity.getQueryData();
			modelMap.addAttribute("detailProjectVO", detailProjectVO);
		}
		
		return "project-show-detail";
	}

	/**
	 * 
	 * 将获取到的值存入session域中，并把获取到的头图片和详情图片进行上传到oss中
	 * @param projectVO
	 * @param headerPicture
	 * @param detailPictureList
	 * @param session
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/create/project/information")
	public String saveProjectBasicInfo(
			// 接收除了上传图片以外的其他内容
			ProjectVO projectVO,
			// 接收上传的头图
			MultipartFile headerPicture,
			// 接收上传的详情图文件
			List<MultipartFile> detailPictureList,
			// 将收集过来的一部分ProjectVO数据存入session域中
			HttpSession session,
			// 用来在当前页面操作失败后返回上一页面携带错误数据
			ModelMap modelMap) throws IOException {

		// 1.先完成头图的上传

		// 判断上传的头图是否为空
		boolean headerPicEmpty = headerPicture.isEmpty();
		if (headerPicEmpty) {
			// 如果为空返回错误信息并回到project-launch页面
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
			return "project-launch";
		}
		// 如果不为空则将图片上传到阿里云oss中
		ResultEntity<String> headUploadResultEntity = CrowdUtil.uploadFileToOss(
				ossProperties.getEndPoint(),
				ossProperties.getAccessKeyId(), 
				ossProperties.getAccessKeySecret(), 
				headerPicture.getInputStream(),
				ossProperties.getBucketName(), 
				ossProperties.getBucketDomain(), 
				headerPicture.getOriginalFilename());
		String headUploadResult = headUploadResultEntity.getOperationResult();
		// 判断图片上传oss是否成功
		if (ResultEntity.FAILED.equals(headUploadResult)) {
			// 如果失败则返回到"project_launch"页面,并将错误信息存入modelMap"MESSAGE_HEADER_PIC_UPLOAD_FAILED"--上传失败
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
			return "project-launch";
		}
		// 如果上传成功，获取图片访问地址
		String headerPicturePath = headUploadResultEntity.getQueryData();
		// 将图片路径存入ProjectVO中
		projectVO.setHeaderPicturePath(headerPicturePath);
		
		
		// 2.上传详情图片
		// 创建一个用来存放详情图片的集合
		List<String> detailPicturePathList = new ArrayList<String>();
		//检查detailPictureList是否有效
		
		if(detailPictureList == null || detailPictureList.size() == 0){
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
			return "project-launch";
		}
		// 遍历detailPictureList数组
		for (MultipartFile detailPicture : detailPictureList) {
			// 判断当前detailPicture是否为空
			if (detailPicture.isEmpty()) {
				modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
				return "project-launch";
			}
			// 如果不为空则将图片上传到阿里云oss中
			ResultEntity<String> detailUploadResultEntity = CrowdUtil.uploadFileToOss(
					ossProperties.getEndPoint(),
					ossProperties.getAccessKeyId(),
					ossProperties.getAccessKeySecret(),
					detailPicture.getInputStream(), 
					ossProperties.getBucketName(), 
					ossProperties.getBucketDomain(),
					detailPicture.getOriginalFilename());
			String detailUploadResult = detailUploadResultEntity.getOperationResult();
			// 判断图片是否上传成功
			if (ResultEntity.SUCCESS.equals(detailUploadResult)) {
				// 获取图片访问路径
				String detailPicturePath = detailUploadResultEntity.getQueryData();
				// 将详情图片路径存入detailPicturePathList
				detailPicturePathList.add(detailPicturePath);
			}else{
				modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
				return "project-launch";
			}
		}

		// 将详情图片列表存入到ProjectVO中
		projectVO.setDetailPicturePathList(detailPicturePathList);

		// 3.将ProjectVO对象存入session域中
		session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);

		// 4.重定向去下一个表单页面
		return "redirect:http://www.crowd.com/project/return/info/page";
	}
	
	/**
	 * 将return页面的图片上传到oss中
	 * 
	 * 在前台页面的javaScript中：formData.append("returnPicture", file);
	 * returnPicture：是请求参数的名字
	 * file：是请求参数的值，也就是要上传的文件
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/create/upload/return/picture.json")
	public ResultEntity<String> uploadReturnPicture(
			//接收用户上传的文件！
			@RequestParam("returnPicture") MultipartFile returnPicture) throws IOException{
		
		//将图片上传到oss中
		ResultEntity<String> returnUploadResultEntity = CrowdUtil.uploadFileToOss(
				ossProperties.getEndPoint(),
				ossProperties.getAccessKeyId(),
				ossProperties.getAccessKeySecret(),
				returnPicture.getInputStream(), 
				ossProperties.getBucketName(), 
				ossProperties.getBucketDomain(),
				returnPicture.getOriginalFilename());
		
		
		//直接返回图片上传的结果
		return returnUploadResultEntity;
	}
	
	/**
	 * 获取return页面的表单数据
	 * 并取出存入进session域中ProjectVO对象
	 * 并将returnVO对象再存入returnVOList集合
	 * 再重新将ProjectVO对象存入session域中，已用来确保新的数据能够存入redis
	 * @param returnVO
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/create/save/return.json")
	public ResultEntity<String> saveReturn(
			//利用ReturnVO来进行获取前台ajax提交的数据
			ReturnVO returnVO,
			//用session来获取出之前存入到session域中的projectvo对象
			HttpSession session){
		
		try {
			//1.从session域中读取出之前缓存的ProjectVO对象
			ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
			
			//2.判断从session域中取出的projectVO是否为空
			if(projectVO == null){
				return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
			}
			
			//3.从projectVO对象中获取存储回报信息的集合
			List<ReturnVO> returnVOList = projectVO.getReturnVOList();
			
			//4.判断returnVOList集合是否有效
			if(returnVOList == null || returnVOList.size() == 0){
				//5.创建几个对象，对returnVOList进行初始化
				returnVOList = new ArrayList<>();
				//6.为了让以后能正常使用这个集合，再将这个集合重新设置到ProjectVO对象中
				projectVO.setReturnVOList(returnVOList);
			}
			//7.将收集了表单数据的returnVO对象存入returnVOList集合
			returnVOList.add(returnVO);
			
			//8.将有数据变化的ProjectVO对象重新存入session域中，已确保新的数据能够存入redis
			session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
			
			//9.所有操作都成功之后返回成功
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}
	
	/**
	 * 
	 * 获取到confirm中的数据通过MemberConfirmInfoVO获取过来
	 * 再查询出ProjectVO对象之前存入session的数据
	 * 再查询出的用户id
	 * 再将MemberConfirmInfoVO数据保存到ProjectVO中
	 * 再通过调用远程接口来进行数据到数据库的保存
	 * 最后再删除ProjectVO对象在session域中临时储存的数据、
	 * 最后跳转到success页面
	 * 
	 * @param session
	 * @param memberConfirmInfoVO
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/create/confirm")
	public String saveConfirm(
			//用来获取之前存入session域中的信息
			HttpSession session,
			//用来接收前台表单发送过来的数据
			MemberConfirmInfoVO memberConfirmInfoVO,
			//用来存入异常信息
			ModelMap modelMap){
		
		//1.获取之前存入session域中的数据
		ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
		
		//2.判断从session域中取出的projectVO是否为空
		if(projectVO == null){
			throw new RuntimeException(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
		}
		
		//3.将确认信息存储到projectVO对象中
		projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);
		
		//4.从session域中获取到该用户的id
		MemberLoginVO loginMember = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		//从获取到的MemberLoginVO对象中获取他的memberId
		Integer memberId = loginMember.getId();
		
		//5.调用远程接口，将数据保存到数据库,数据：(ProjectVO对象和该用户的id-memberId)
		ResultEntity<String> saveResultEntity= mySQLRemoteService.saveProjectVORemote(projectVO,memberId);
		if(ResultEntity.FAILED.equals(saveResultEntity.getOperationResult())){
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveResultEntity.getOperationMessage());
			return "project-confirm";
		}
		
		//6.将临时存入session中的projectvo对象从session域中删除
		session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
		return "redirect:http://www.crowd.com/project/create/success";
	}

}
