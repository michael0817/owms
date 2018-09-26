package com.bootdo.order.service;

import com.bootdo.order.domain.FieldMappingDO;

import java.util.List;
import java.util.Map;

/**
 * 模板字段映射
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-26 01:14:05
 */
public interface FieldMappingService {
	
	FieldMappingDO get(Integer moduleId);
	
	List<FieldMappingDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(FieldMappingDO fieldMapping);
	
	int update(FieldMappingDO fieldMapping);
	
	int remove(Integer moduleId);
	
	int batchRemove(Integer[] moduleIds);
}
