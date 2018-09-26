package com.bootdo.order.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 模板字段映射
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-26 01:14:05
 */
public class FieldMappingDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//模板编号
	private Integer moduleId;
	//文件类型
	private Integer moduleType;
	//业务字段
	private String businessFieldName;
	//EXCEL字段名
	private String excelFieldName;

	/**
	 * 设置：模板编号
	 */
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	/**
	 * 获取：模板编号
	 */
	public Integer getModuleId() {
		return moduleId;
	}
	/**
	 * 设置：文件类型
	 */
	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}
	/**
	 * 获取：文件类型
	 */
	public Integer getModuleType() {
		return moduleType;
	}
	/**
	 * 设置：业务字段
	 */
	public void setBusinessFieldName(String businessFieldName) {
		this.businessFieldName = businessFieldName;
	}
	/**
	 * 获取：业务字段
	 */
	public String getBusinessFieldName() {
		return businessFieldName;
	}
	/**
	 * 设置：EXCEL字段名
	 */
	public void setExcelFieldName(String excelFieldName) {
		this.excelFieldName = excelFieldName;
	}
	/**
	 * 获取：EXCEL字段名
	 */
	public String getExcelFieldName() {
		return excelFieldName;
	}
}
