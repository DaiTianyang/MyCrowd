package com.atguigu.crowd.entity.vo;

public class DetailReturnVO {

	// 回报信息的主键
	private Integer returnId;

	// 当前挡位支持的金额
	private Integer supportMoney;

	// 当前挡位支持的数量
	private Integer supporterCount;

	// 单笔限购数量：为0表示无限额的，取值为1表示有限额的
	private Integer signalPurchase;

	// 具体的限额数量
	private Integer purchase;

	// 运费：为0表示包邮
	private Integer freight;

	// 众筹成功后多少天发货
	private Integer returnDate;

	// 回报内容
	private String content;

	public DetailReturnVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DetailReturnVO(Integer returnId, Integer supportMoney, Integer supporterCount, Integer signalPurchase,
			Integer purchase, Integer freight, Integer returnDate, String content) {
		super();
		this.returnId = returnId;
		this.supportMoney = supportMoney;
		this.supporterCount = supporterCount;
		this.signalPurchase = signalPurchase;
		this.purchase = purchase;
		this.freight = freight;
		this.returnDate = returnDate;
		this.content = content;
	}

	public Integer getReturnId() {
		return returnId;
	}

	public void setReturnId(Integer returnId) {
		this.returnId = returnId;
	}

	public Integer getSupportMoney() {
		return supportMoney;
	}

	public void setSupportMoney(Integer supportMoney) {
		this.supportMoney = supportMoney;
	}

	public Integer getSupporterCount() {
		return supporterCount;
	}

	public void setSupporterCount(Integer supporterCount) {
		this.supporterCount = supporterCount;
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

	public Integer getFreight() {
		return freight;
	}

	public void setFreight(Integer freight) {
		this.freight = freight;
	}

	public Integer getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Integer returnDate) {
		this.returnDate = returnDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "DetailReturnVO [returnId=" + returnId + ", supportMoney=" + supportMoney + ", supporterCount="
				+ supporterCount + ", signalPurchase=" + signalPurchase + ", purchase=" + purchase + ", freight="
				+ freight + ", returnDate=" + returnDate + ", content=" + content + "]";
	}

}
