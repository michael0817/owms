package com.bootdo.order.service.impl;

import com.bootdo.order.dao.OrderDao;
import com.bootdo.order.domain.OrderDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bootdo.order.service.OrderService;



@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;
	
	@Override
	public OrderDO get(Long orderId){
		return orderDao.get(orderId);
	}
	
	@Override
	public List<OrderDO> list(Map<String, Object> map){
		return orderDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return orderDao.count(map);
	}
	
	@Override
	public int save(OrderDO list){
		return orderDao.save(list);
	}
	
	@Override
	public int update(OrderDO list){
		return orderDao.update(list);
	}
	
	@Override
	public int remove(Long orderId){
		return orderDao.remove(orderId);
	}

	@Override
	public int removeByDate(Date createDate){
		return orderDao.removeByDate(createDate);
	}
	
	@Override
	public int batchRemove(Long[] orderIds){
		return orderDao.batchRemove(orderIds);
	}
	
}
