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
public class OrderBatchDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //订单编号
    private String orderId;
    //导入批次
    private Integer orderBatch;
    //导入日期
    private Date batchDate;

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
     * 设置：导入批次
     */
    public void setOrderBatch(Integer orderBatch) {
        this.orderBatch = orderBatch;
    }

    /**
     * 获取：导入批次
     */
    public Integer getOrderBatch() {
        return orderBatch;
    }

    /**
     * 设置：导入日期
     */
    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
    }

    /**
     * 获取：导入日期
     */
    public Date getBatchDate() {
        return batchDate;
    }

}
