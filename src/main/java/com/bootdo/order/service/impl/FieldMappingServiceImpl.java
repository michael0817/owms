package com.bootdo.order.service.impl;

import com.bootdo.order.dao.FieldMappingDao;
import com.bootdo.order.domain.FieldMappingDO;
import com.bootdo.order.service.FieldMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class FieldMappingServiceImpl implements FieldMappingService {
	@Autowired
	private FieldMappingDao fieldMappingDao;

	@Override
	public FieldMappingDO get(Long moduleId){
		return fieldMappingDao.get(moduleId);
	}
	
	@Override
	public List<FieldMappingDO> list(Map<String, Object> map){ return fieldMappingDao.list(map); }
	
	@Override
	public int count(Map<String, Object> map){
		return fieldMappingDao.count(map);
	}
	
	@Override
	public int save(FieldMappingDO fieldMapping){
		return fieldMappingDao.save(fieldMapping);
	}
	
	@Override
	public int update(FieldMappingDO fieldMapping){
		return fieldMappingDao.update(fieldMapping);
	}
	
	@Override
	public int remove(Long moduleId){
		return fieldMappingDao.remove(moduleId);
	}
	
	@Override
	public int batchRemove(Long[] moduleIds){
		return fieldMappingDao.batchRemove(moduleIds);
	}
	
}
