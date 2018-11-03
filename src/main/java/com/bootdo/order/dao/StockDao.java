package com.bootdo.order.dao;

import com.bootdo.order.domain.StockDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 库存信息
 * @author xumx
 * @email michael0817@126.com
 * @date 2018-10-23 03:02:38
 */
@Mapper
public interface StockDao {

	StockDO get(@Param("warehouseCode") String warehouseCode, @Param("sku") String sku);
	
	List<StockDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(StockDO list);
	
	int update(StockDO list);
	
	int remove(String warehouse_code, String sku);
}
