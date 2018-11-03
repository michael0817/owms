package com.bootdo.order.service;

import com.bootdo.order.domain.StockDO;

import java.util.List;
import java.util.Map;

/**
 * 库存信息
 * 
 * @author xumx
 * @email michael0817@126.com
 * @date 2018-10-23 03:02:38
 */
public interface StockService {

	StockDO get(String warehouseCode, String sku);

	List<StockDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(StockDO stock);

	int update(StockDO stock);

	int remove(String warehouseCode, String sku);
}
