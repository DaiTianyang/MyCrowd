package com.atguigu.crowd.entity.vo;

public class PortalProjectVO {

	//众筹项目的id
	private Integer projectId;
	//众筹项目的name
	private String projectName;
	//众筹项目的头图片路径
	private String headerPicturePath;
	//众筹项目的众筹金额
	private Integer money;
	//众筹项目的截至日期
	private String deployDate;
	//众筹项目的众筹金额百分比：例如想要筹款共10000元已经筹款8000元则该字段表示为80%
	private Integer percentage;
	//众筹项目的支持人数
	private Integer supporter;
	
	
	public PortalProjectVO(Integer id, String projectName, String headerPicturePath, Integer money, String deployDate,
			Integer percentage, Integer supporter) {
		super();
		this.projectId = id;
		this.projectName = projectName;
		this.headerPicturePath = headerPicturePath;
		this.money = money;
		this.deployDate = deployDate;
		this.percentage = percentage;
		this.supporter = supporter;
	}
	public PortalProjectVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return projectId;
	}
	public void setId(Integer id) {
		this.projectId = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getHeaderPicturePath() {
		return headerPicturePath;
	}
	public void setHeaderPicturePath(String headerPicturePath) {
		this.headerPicturePath = headerPicturePath;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public String getDeployDate() {
		return deployDate;
	}
	public void setDeployDate(String deployDate) {
		this.deployDate = deployDate;
	}
	public Integer getPercentage() {
		return percentage;
	}
	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}
	public Integer getSupporter() {
		return supporter;
	}
	public void setSupporter(Integer supporter) {
		this.supporter = supporter;
	}
	@Override
	public String toString() {
		return "PortalProjectVO [id=" + projectId + ", projectName=" + projectName + ", headerPicturePath=" + headerPicturePath
				+ ", money=" + money + ", deployDate=" + deployDate + ", percentage=" + percentage + ", supporter="
				+ supporter + "]";
	}
	
	
}
