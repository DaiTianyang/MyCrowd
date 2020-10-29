package com.atguigu.crowd.entity.vo;

import java.io.Serializable;

public class OrderProjectVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//id
	private Integer id;
	//项目名称
	private String projectName;
	//发起者名称
	private String launchName;
	//回报详情
	private String returnContent;
	//回报金额
	private Integer returnCount;
	//支持金额
	private Integer supportPrice;
	//配送费用
	private Integer freight;
	//orderId
	private Integer orderId;
	//单笔限购数量
	private Integer signalPurchase;
	//购买数量
	private Integer purchase;
	
	
	
	public OrderProjectVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderProjectVo(Integer id, String projectName, String launchName, String returnContent, Integer returnCount,
			Integer supportPrice, Integer freight, Integer orderId, Integer signalPurchase, Integer purchase) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.launchName = launchName;
		this.returnContent = returnContent;
		this.returnCount = returnCount;
		this.supportPrice = supportPrice;
		this.freight = freight;
		this.orderId = orderId;
		this.signalPurchase = signalPurchase;
		this.purchase = purchase;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getLaunchName() {
		return launchName;
	}
	public void setLaunchName(String launchName) {
		this.launchName = launchName;
	}
	public String getReturnContent() {
		return returnContent;
	}
	public void setReturnContent(String returnContent) {
		this.returnContent = returnContent;
	}
	public Integer getReturnCount() {
		return returnCount;
	}
	public void setReturnCount(Integer returnCount) {
		this.returnCount = returnCount;
	}
	public Integer getSupportPrice() {
		return supportPrice;
	}
	public void setSupportPrice(Integer supportPrice) {
		this.supportPrice = supportPrice;
	}
	public Integer getFreight() {
		return freight;
	}
	public void setFreight(Integer freight) {
		this.freight = freight;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getSignalPurchase() {
		return signalPurchase;
	}
	public void setSignalPurchase(Integer signalPurchase) {
		this.signalPurchase = signalPurchase;
	}
	public Integer getPurchase() {
		return purchase;
	}
	public void setPurchase(Integer purchase) {
		this.purchase = purchase;
	}
	@Override
	public String toString() {
		return "OrderProjectVo [id=" + id + ", projectName=" + projectName + ", launchName=" + launchName
				+ ", returnContent=" + returnContent + ", returnCount=" + returnCount + ", supportPrice=" + supportPrice
				+ ", freight=" + freight + ", orderId=" + orderId + ", signalPurchase=" + signalPurchase + ", purchase="
				+ purchase + "]";
	}
	
	
}
