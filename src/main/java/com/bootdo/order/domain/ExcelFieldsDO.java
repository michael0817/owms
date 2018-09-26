package com.bootdo.order.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * Excel模板字段
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-26 20:15:41
 */
public class ExcelFieldsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//模板文件名
	private String moduleName;
	//EXCEL字段名
	private String excelFieldName;

	/**
	 * 设置：模板文件名
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	/**
	 * 获取：模板文件名
	 */
	public String getModuleName() {
		return moduleName;
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
