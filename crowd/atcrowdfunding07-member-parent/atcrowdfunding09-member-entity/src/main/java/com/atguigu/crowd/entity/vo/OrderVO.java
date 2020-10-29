package com.atguigu.crowd.entity.vo;

import java.io.Serializable;

public class OrderVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	// 订单号
	private String orderNum;
	// 支付宝流水单号
	private String payOrderNum;
	// 订单金额
	private Double orderAmount;
	// 是否开发票 value=1开 value=0不开
	private Integer invoice;
	// 发票抬头
	private String invoiceTitle;
	// 备注
	private String orderRemark;
	// 收货地址的id
	private String addressId;
	// OrderProjectVo
	private OrderProjectVo orderProjectVo;

	public OrderVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderVO(Integer id, String orderNum, String payOrderNum, Double orderAmount, Integer invoice,
			String invoiceTitle, String orderRemark, String addressId, OrderProjectVo orderProjectVo) {
		super();
		this.id = id;
		this.orderNum = orderNum;
		this.payOrderNum = payOrderNum;
		this.orderAmount = orderAmount;
		this.invoice = invoice;
		this.invoiceTitle = invoiceTitle;
		this.orderRemark = orderRemark;
		this.addressId = addressId;
		this.orderProjectVo = orderProjectVo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getPayOrderNum() {
		return payOrderNum;
	}

	public void setPayOrderNum(String payOrderNum) {
		this.payOrderNum = payOrderNum;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getInvoice() {
		return invoice;
	}

	public void setInvoice(Integer invoice) {
		this.invoice = invoice;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public OrderProjectVo getOrderProjectVo() {
		return orderProjectVo;
	}

	public void setOrderProjectVo(OrderProjectVo orderProjectVo) {
		this.orderProjectVo = orderProjectVo;
	}

	@Override
	public String toString() {
		return "OrderVO [id=" + id + ", orderNum=" + orderNum + ", payOrderNum=" + payOrderNum + ", orderAmount="
				+ orderAmount + ", invoice=" + invoice + ", invoiceTitle=" + invoiceTitle + ", orderRemark="
				+ orderRemark + ", addressId=" + addressId + ", orderProjectVo=" + orderProjectVo + "]";
	}

}
