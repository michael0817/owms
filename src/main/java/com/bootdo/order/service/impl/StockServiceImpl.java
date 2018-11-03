package com.bootdo.order.service.impl;

import com.bootdo.order.domain.StockDO;
import com.bootdo.order.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.order.dao.StockDao;


@Service
public class StockServiceImpl implements StockService {
	@Autowired
	private StockDao stockDao;

	@Override
	public StockDO get(String warehouseCode, String sku){
		return stockDao.get(warehouseCode, sku);
	}

	@Override
	public List<StockDO> list(Map<String, Object> map){
		return stockDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return stockDao.count(map);
	}
	
	@Override
	public int save(StockDO stock){
		return stockDao.save(stock);
	}
	
	@Override
	public int update(StockDO stock){
		return stockDao.update(stock);
	}
	
	@Override
	public int remove(String warehouseCode, String sku){
		return stockDao.remove(warehouseCode, sku);
	}
	
}
