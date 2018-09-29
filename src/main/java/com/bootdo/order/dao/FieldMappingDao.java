package com.bootdo.order.dao;

import com.bootdo.order.domain.FieldMappingDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 模板字段映射
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-26 01:14:05
 */
@Mapper
public interface FieldMappingDao {

	FieldMappingDO get(Long moduleId);
	
	List<FieldMappingDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(FieldMappingDO fieldMapping);
	
	int update(FieldMappingDO fieldMapping);
	
	int remove(Long moduleId);
	
	int batchRemove(Long[] moduleIds);
}
