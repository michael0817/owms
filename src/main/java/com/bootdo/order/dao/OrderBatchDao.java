package com.bootdo.order.dao;

import com.bootdo.order.domain.OrderBatchDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单模板
 *
 * @author xumx
 * @email michael0817@126.com
 * @date 2018-09-22 15:18:03
 */
@Mapper
public interface OrderBatchDao {

    List<OrderBatchDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(OrderBatchDO orderBatch);

    int remove(Date batchDate, Integer orderBatch);

    Integer getMaxBatch(Date batchDate);

    List<Integer> getOrderBatchNums(Date createDate);
}
