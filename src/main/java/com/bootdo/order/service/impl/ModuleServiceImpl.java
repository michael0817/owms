package com.bootdo.order.service.impl;

import com.bootdo.order.dao.ModuleDao;
import com.bootdo.order.domain.ModuleDO;
import com.bootdo.order.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ModuleServiceImpl implements ModuleService {
	@Autowired
	private ModuleDao moduleDao;
	
	@Override
	public ModuleDO get(Long moduleId){
		return moduleDao.get(moduleId);
	}
	
	@Override
	public List<ModuleDO> list(Map<String, Object> map){
		return moduleDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return moduleDao.count(map);
	}
	
	@Override
	public int save(ModuleDO module){
		return moduleDao.save(module);
	}
	
	@Override
	public int update(ModuleDO module){
		return moduleDao.update(module);
	}
	
	@Override
	public int remove(Long moduleId){
		return moduleDao.remove(moduleId);
	}
	
	@Override
	public int batchRemove(Long[] moduleIds){
		return moduleDao.batchRemove(moduleIds);
	}
	
}
