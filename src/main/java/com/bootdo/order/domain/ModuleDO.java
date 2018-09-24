package com.bootdo.order.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 订单模板
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-22 16:27:23
 */
public class ModuleDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long moduleId;
	//文件类型(1-订单宝模板;2-各平台订单模板;3-运单模板)
	private Integer type;
	//前缀
	private String prefix;
	//URL地址
	private String url;
	//创建日期
	private Date createDate;

	/**
	 * 设置：
	 */
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	/**
	 * 获取：
	 */
	public Long getModuleId() {
		return moduleId;
	}
	/**
	 * 设置：文件类型(1-订单宝模板;2-各平台订单模板;3-运单模板)
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：文件类型(1-订单宝模板;2-各平台订单模板;3-运单模板)
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：前缀
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	/**
	 * 获取：前缀
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * 设置：URL地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：URL地址
	 */
	public String getUrl() {
		return url;
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
