package com.atguigu.crowd.entity.vo;

import java.util.List;

public class DetailProjectVO {

	//众筹项目的id
	private Integer projectId;
	//众筹项目的name
	private String projectName;
	//众筹项目的简介
	private String projectDesc;
	//众筹项目的关注人数
	private Integer followerCount;
	//众筹项目的当前状态
	private Integer status;
	//众筹项目的当前状态
	private String statusText;
	//众筹项目的众筹金额
	private Integer money;
	//众筹持续时间
	private Integer day;
	//众筹项目已经筹到的金额
	private Integer supportMoney;
	//众筹项目完成度百分比
	private Integer percentage;
	//众筹项目的截至日期
	private String deployDate;
	//众筹项目的剩余日期
	private Integer lastDay;
	//众筹项目的支持者数量
	private Integer supporterCount;
	//众筹项目的头图路径
	private String headerPicturePath;
	//众筹项目的详情信息图片地址
	private List<String> detailPicturePathList;
	//众筹项目的回报信息集合
	private List<DetailReturnVO> detailReturnVOList;
	
	
	public DetailProjectVO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DetailProjectVO(Integer projectId, String projectName, String projectDesc, Integer followerCount,
			Integer status, String statusText, Integer money, Integer day, Integer supportMoney, Integer percentage,
			String deployDate, Integer lastDay, Integer supporterCount, String headerPicturePath,
			List<String> detailPicturePathList, List<DetailReturnVO> detailReturnVOList) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectDesc = projectDesc;
		this.followerCount = followerCount;
		this.status = status;
		this.statusText = statusText;
		this.money = money;
		this.day = day;
		this.supportMoney = supportMoney;
		this.percentage = percentage;
		this.deployDate = deployDate;
		this.lastDay = lastDay;
		this.supporterCount = supporterCount;
		this.headerPicturePath = headerPicturePath;
		this.detailPicturePathList = detailPicturePathList;
		this.detailReturnVOList = detailReturnVOList;
	}


	public Integer getProjectId() {
		return projectId;
	}


	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getProjectDesc() {
		return projectDesc;
	}


	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}


	public Integer getFollowerCount() {
		return followerCount;
	}


	public void setFollowerCount(Integer followerCount) {
		this.followerCount = followerCount;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getStatusText() {
		return statusText;
	}


	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}


	public Integer getMoney() {
		return money;
	}


	public void setMoney(Integer money) {
		this.money = money;
	}


	public Integer getDay() {
		return day;
	}


	public void setDay(Integer day) {
		this.day = day;
	}


	public Integer getSupportMoney() {
		return supportMoney;
	}


	public void setSupportMoney(Integer supportMoney) {
		this.supportMoney = supportMoney;
	}


	public Integer getPercentage() {
		return percentage;
	}


	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}


	public String getDeployDate() {
		return deployDate;
	}


	public void setDeployDate(String deployDate) {
		this.deployDate = deployDate;
	}


	public Integer getLastDay() {
		return lastDay;
	}


	public void setLastDay(Integer lastDay) {
		this.lastDay = lastDay;
	}


	public Integer getSupporterCount() {
		return supporterCount;
	}


	public void setSupporterCount(Integer supporterCount) {
		this.supporterCount = supporterCount;
	}


	public String getHeaderPicturePath() {
		return headerPicturePath;
	}


	public void setHeaderPicturePath(String headerPicturePath) {
		this.headerPicturePath = headerPicturePath;
	}


	public List<String> getDetailPicturePathList() {
		return detailPicturePathList;
	}


	public void setDetailPicturePathList(List<String> detailPicturePathList) {
		this.detailPicturePathList = detailPicturePathList;
	}


	public List<DetailReturnVO> getDetailReturnVOList() {
		return detailReturnVOList;
	}


	public void setDetailReturnVOList(List<DetailReturnVO> detailReturnVOList) {
		this.detailReturnVOList = detailReturnVOList;
	}


	@Override
	public String toString() {
		return "DetailProjectVO [projectId=" + projectId + ", projectName=" + projectName + ", projectDesc="
				+ projectDesc + ", followerCount=" + followerCount + ", status=" + status + ", statusText=" + statusText
				+ ", money=" + money + ", day=" + day + ", supportMoney=" + supportMoney + ", percentage=" + percentage
				+ ", deployDate=" + deployDate + ", lastDay=" + lastDay + ", supporterCount=" + supporterCount
				+ ", headerPicturePath=" + headerPicturePath + ", detailPicturePathList=" + detailPicturePathList
				+ ", detailReturnVOList=" + detailReturnVOList + "]";
	}

	
	
}
