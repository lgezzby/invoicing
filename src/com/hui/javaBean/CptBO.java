package com.hui.javaBean;

import java.util.Date;

public class CptBO implements java.io.Serializable {
	private String id;
	private Date gmtCreated;
	private String customerId;
	private String name;
	private String mobile;
	private String farm;
	private String demand;
	private String specification;
	private String directive;
	private String location;
	private String chromaticNumber;
	private String method;
	private String remark;
	private String userSigned;

	public CptBO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFarm() {
		return farm;
	}

	public void setFarm(String farm) {
		this.farm = farm;
	}

	public String getDemand() {
		return demand;
	}

	public void setDemand(String demand) {
		this.demand = demand;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getDirective() {
		return directive;
	}

	public void setDirective(String directive) {
		this.directive = directive;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getChromaticNumber() {
		return chromaticNumber;
	}

	public void setChromaticNumber(String chromaticNumber) {
		this.chromaticNumber = chromaticNumber;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserSigned() {
		return userSigned;
	}

	public void setUserSigned(String userSigned) {
		this.userSigned = userSigned;
	}
}