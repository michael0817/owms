package com.bootdo.order.service.impl;

import com.bootdo.order.dao.FieldMappingDao;
import com.bootdo.order.domain.FieldMappingDO;
import com.bootdo.order.enums.ModuleType;
import com.bootdo.order.service.FieldMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class FieldMappingServiceImpl implements FieldMappingService {
    @Autowired
    private FieldMappingDao fieldMappingDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public FieldMappingDO get(Long moduleId) {
        return fieldMappingDao.get(moduleId);
    }

    @Override
    public List<FieldMappingDO> list(Map<String, Object> map) {
        return fieldMappingDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return fieldMappingDao.count(map);
    }

    @Override
    public int save(FieldMappingDO fieldMapping) {
        return fieldMappingDao.save(fieldMapping);
    }

    @Override
    public int update(FieldMappingDO fieldMapping) {
        return fieldMappingDao.update(fieldMapping);
    }

    @Override
    public int remove(Long moduleId) {
        return fieldMappingDao.remove(moduleId);
    }

    @Override
    public int batchRemove(Long[] moduleIds) {
        return fieldMappingDao.batchRemove(moduleIds);
    }

    @Override
    public List<Map<String, Object>> getFieldMapping(Long moduleId,ModuleType moduleType) {
        List<Map<String, Object>> excelFieldMapList = jdbcTemplate.queryForList("select t.excel_field_name,r.value from order_field_mapping t,sys_dict r " +
                "where t.module_id=? and r.type=? and t.business_field_name = r.name;", new Object[]{moduleId, "order_module_"+moduleType.getIndex()});
        return excelFieldMapList;
    }

}
