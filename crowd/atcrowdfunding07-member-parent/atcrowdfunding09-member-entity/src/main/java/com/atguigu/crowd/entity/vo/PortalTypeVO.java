package com.atguigu.crowd.entity.vo;

import java.util.List;

public class PortalTypeVO {

	//type里面的id
	private Integer id;
	//type里面的类别的名称，例如：科技
	private String name;
	//类别名称的详细描述，例如：开启智慧未来
	private String remark;
	//这个是使用了这个typel类型的相关Project的数组
	private List<PortalProjectVO> portalProjectVOList;
	
	public PortalTypeVO(Integer id, String name, String remark, List<PortalProjectVO> portalProjectVOList) {
		super();
		this.id = id;
		this.name = name;
		this.remark = remark;
		this.portalProjectVOList = portalProjectVOList;
	}
	public PortalTypeVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<PortalProjectVO> getPortalProjectVOList() {
		return portalProjectVOList;
	}
	public void setPortalProjectVOList(List<PortalProjectVO> portalProjectVOList) {
		this.portalProjectVOList = portalProjectVOList;
	}
	@Override
	public String toString() {
		return "PortalTypeVO [id=" + id + ", name=" + name + ", remark=" + remark + ", portalProjectVOList="
				+ portalProjectVOList + "]";
	}
	
	
	
}
