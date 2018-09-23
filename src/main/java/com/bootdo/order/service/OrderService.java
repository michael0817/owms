package com.bootdo.order.service;

import com.bootdo.order.domain.OrderDO;

import java.util.List;
import java.util.Map;

/**
 * 订单模板
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-22 15:18:03
 */
public interface OrderService {
	
	OrderDO get(Long orderId);
	
	List<OrderDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OrderDO list);
	
	int update(OrderDO list);
	
	int remove(Long orderId);
	
	int batchRemove(Long[] orderIds);
}
