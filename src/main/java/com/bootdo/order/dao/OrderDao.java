package com.bootdo.order.dao;

import com.bootdo.order.domain.OrderDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 订单模板
 * @author xumx
 * @email michael0817@126.com
 * @date 2018-09-22 15:18:03
 */
@Mapper
public interface OrderDao {

	OrderDO get(Long orderId);
	
	List<OrderDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(OrderDO order);
	
	int update(OrderDO order);
	
	int remove(Long orderId);
	
	int batchRemove(Long[] orderIds);
}
