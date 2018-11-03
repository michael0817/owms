package com.bootdo.order.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 库存信息
 * 
 * @author xumx
 * @email michael0817@126.com
 * @date 2018-10-23 03:02:38
 */
public class StockDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//仓库编号
	private String warehouseCode;
	//SKU
	private String sku;
	//库存
	private Integer stocks;
	//可用库存
	private Integer actualStocks;
    //库存是否足够
	private Integer availableFlag;
	//更新日期
	private Date dataDate;

	/**
	 * 设置：仓库编号
	 */
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	/**
	 * 获取：仓库编号
	 */
	public String getWarehouseCode() {
		return warehouseCode;
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
	 * 设置：库存
	 */
	public void setStocks(Integer stocks) {
		this.stocks = stocks;
	}
	/**
	 * 获取：库存
	 */
	public Integer getStocks() {
		return stocks;
	}
    /**
     * 设置：可用库存
     */
    public void setActualStocks(Integer actualStocks) {
        this.actualStocks = actualStocks;
    }
    /**
     * 获取：可用库存
     */
    public Integer getActualStocks() {
        return actualStocks;
    }

    /**
     * 设置：库存是否足够
     */
    public void setAvailableFlag(Integer availableFlag) {
        this.availableFlag = availableFlag;
    }
    /**
     * 获取：库存是否足够
     */
    public Integer getAvailableFlag() {
        return availableFlag;
    }

	/**
	 * 设置：更新日期
	 */
	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}
	/**
	 * 获取：更新日期
	 */
	public Date getDataDate() {
		return dataDate;
	}
}
