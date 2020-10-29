package com.atguigu.crowd.entity.vo;

import java.io.Serializable;

public class AddressVO implements Serializable {

	private static final long seriaVersionUID = 1L;
	// address的id
	private Integer id;
	// 收货人姓名
	private String receiveName;
	// 电话号码
	private String phoneNum;
	// 详细地址
	private String address;
	// 用户id
	private Integer memberId;

	public AddressVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddressVO(Integer id, String receiveName, String phoneNum, String address, Integer memberId) {
		super();
		this.id = id;
		this.receiveName = receiveName;
		this.phoneNum = phoneNum;
		this.address = address;
		this.memberId = memberId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "AddressVO [id=" + id + ", receiveName=" + receiveName + ", phoneNum=" + phoneNum + ", address="
				+ address + ", memberId=" + memberId + "]";
	}

}
