package com.bootdo.order.dao;

import com.bootdo.order.domain.OrderDO;
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
public interface OrderDao {

    OrderDO get(String orderId);

    List<OrderDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(OrderDO order);

    int update(OrderDO order);

    int remove(String orderId);

    int removeByDate(Date createDate);

    int batchRemove(String[] orderIds);
}
