package com.bootdo.order.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 订单详情
 * 
 * @author xumx
 * @email michael0817@126.com
 * @date 2018-09-22 15:18:03
 */
public class OrderDO implements Serializable {
	private static final long serialVersionUID = 1L;

	//订单编号
	private String orderId;
	//模板编号
	private Long moduleId;
	//仓库代码
	private String warehouseCode;
	//配送方式
	private String deliveryStyle;
	//保险类型
	private String insuranceType;
	//投保金额
	private String insuranceValue;
	//收件人姓名
	private String consigneeName;
	//收件人电话
	private String consigneePhone;
	//收件人身份证号
	private String consigneeId;
	//收件人国家
	private String consigneeCountry;
	//州/省
	private String province;
	//城市/市
	private String city;
	//街道
	private String street;
	//门牌号/区
	private String doorplate;
	//邮编
	private String zipCode;
	//SKU
	private String sku;
	//数量
	private String quntity;
	//创建日期
	private Date createDate;

	/**
	 * 设置：订单编号
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单编号
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * 设置：模板编号
	 */
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	/**
	 * 获取：模板编号
	 */
	public Long getModuleId() {
		return moduleId;
	}
	/**
	 * 设置：仓库代码
	 */
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	/**
	 * 获取：仓库代码
	 */
	public String getWarehouseCode() {
		return warehouseCode;
	}
	/**
	 * 设置：配送方式
	 */
	public void setDeliveryStyle(String deliveryStyle) {
		this.deliveryStyle = deliveryStyle;
	}
	/**
	 * 获取：配送方式
	 */
	public String getDeliveryStyle() {
		return deliveryStyle;
	}
	/**
	 * 设置：保险类型
	 */
	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}
	/**
	 * 获取：保险类型
	 */
	public String getInsuranceType() {
		return insuranceType;
	}
	/**
	 * 设置：投保金额
	 */
	public void setInsuranceValue(String insuranceValue) {
		this.insuranceValue = insuranceValue;
	}
	/**
	 * 获取：投保金额
	 */
	public String getInsuranceValue() {
		return insuranceValue;
	}
	/**
	 * 设置：收件人姓名
	 */
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	/**
	 * 获取：收件人姓名
	 */
	public String getConsigneeName() {
		return consigneeName;
	}
	/**
	 * 设置：收件人电话
	 */
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	/**
	 * 获取：收件人电话
	 */
	public String getConsigneePhone() {
		return consigneePhone;
	}
	/**
	 * 设置：收件人身份证号
	 */
	public void setConsigneeId(String consigneeId) {
		this.consigneeId = consigneeId;
	}
	/**
	 * 获取：收件人身份证号
	 */
	public String getConsigneeId() {
		return consigneeId;
	}
	/**
	 * 设置：收件人国家
	 */
	public void setConsigneeCountry(String consigneeCountry) {
		this.consigneeCountry = consigneeCountry;
	}
	/**
	 * 获取：收件人国家
	 */
	public String getConsigneeCountry() {
		return consigneeCountry;
	}
	/**
	 * 设置：州/省
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * 获取：州/省
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * 设置：城市/市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：城市/市
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：街道
	 */
	public void setStreet(String street) { this.street = street; }
	/**
	 * 获取：街道
	 */
	public String getStreet() { return street; }
	/**
	 * 设置：门牌号/区
	 */
	public void setDoorplate(String doorplate) {
		this.doorplate = doorplate;
	}
	/**
	 * 获取：门牌号/区
	 */
	public String getDoorplate() {
		return doorplate;
	}
	/**
	 * 设置：邮编
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * 获取：邮编
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * 设置：SKU
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}
	/**
	 * 获取：SKU
	 */
	public String getSku() {
		return sku;
	}
	/**
	 * 设置：数量
	 */
	public void setQuntity(String quntity) {
		this.quntity = quntity;
	}
	/**
	 * 获取：数量
	 */
	public String getQuntity() {
		return quntity;
	}
	/**
	 * 设置：创建日期
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建日期
	 */
	public Date getCreateDate() {
		return createDate;
	}
}
