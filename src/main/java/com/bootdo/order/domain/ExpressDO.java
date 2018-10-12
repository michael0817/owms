package com.bootdo.order.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 运单详情
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-30 14:03:43
 */
public class ExpressDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //订单编号
    private String orderId;
    //SKU
    private String sku;
    //运单号
    private String expressId;
    //物流公司
    private String expressCompany;
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
     * 设置：运单号
     */
    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    /**
     * 获取：运单号
     */
    public String getExpressId() {
        return expressId;
    }

    /**
     * 设置：物流公司
     */
    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    /**
     * 获取：物流公司
     */
    public String getExpressCompany() {
        return expressCompany;
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
