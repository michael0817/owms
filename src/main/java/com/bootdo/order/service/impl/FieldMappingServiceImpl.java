package com.bootdo.order.service.impl;

import com.bootdo.common.dao.DictDao;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.order.dao.FieldMappingDao;
import com.bootdo.order.domain.FieldMappingDO;
import com.bootdo.order.service.FieldMappingService;



@Service
public class FieldMappingServiceImpl implements FieldMappingService {
	@Autowired
	private FieldMappingDao fieldMappingDao;
	@Autowired
	private DictService dictService;
	
	@Override
	public FieldMappingDO get(Integer moduleId){
		return fieldMappingDao.get(moduleId);
	}
	
	@Override
	public List<FieldMappingDO> list(Map<String, Object> map){

        List<FieldMappingDO> fmList = fieldMappingDao.list(map);

        if(fmList.size()==0){
            Map dictMap = new HashMap();
            if("1".equals(map.get("moduleType").toString())){//特殊处理，平台订单模板复用订单宝模板字段
                dictMap.put("type","order_module_2");
            }else{
                dictMap.put("type","order_module_"+map.get("moduleType"));
            }

            List<DictDO> dictList = dictService.list(dictMap);
            for(DictDO dictDO : dictList){
                FieldMappingDO fmDO = new FieldMappingDO();
                fmDO.setModuleType(Integer.parseInt(map.get("moduleType").toString()));
                fmDO.setModuleId(Integer.parseInt(map.get("moduleId").toString()));
                fmDO.setBusinessFieldName(dictDO.getName());
                fmList.add(fmDO);
            }
        }
		return fmList;
	}
	
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
	public int remove(Integer moduleId){
		return fieldMappingDao.remove(moduleId);
	}
	
	@Override
	public int batchRemove(Integer[] moduleIds){
		return fieldMappingDao.batchRemove(moduleIds);
	}
	
}
