package com.bootdo.order.service;

import com.bootdo.order.domain.OrderDO;

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
public interface OrderService {

    OrderDO get(String orderId);

    List<OrderDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(OrderDO list);

    int update(OrderDO list);

    int removeByDate(Date createDate);

    int remove(String orderId);

    int batchRemove(String[] orderIds);
}
