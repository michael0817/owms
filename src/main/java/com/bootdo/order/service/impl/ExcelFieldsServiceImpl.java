package com.bootdo.order.service.impl;

import com.bootdo.order.dao.ExcelFieldsDao;
import com.bootdo.order.domain.ExcelFieldsDO;
import com.bootdo.order.service.ExcelFieldsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ExcelFieldsServiceImpl implements ExcelFieldsService {
    @Autowired
    private ExcelFieldsDao excelFieldsDao;

    @Override
    public ExcelFieldsDO get(String moduleName) {
        return excelFieldsDao.get(moduleName);
    }

    @Override
    public List<ExcelFieldsDO> list(Map<String, Object> map) {
        return excelFieldsDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return excelFieldsDao.count(map);
    }

    @Override
    public int save(ExcelFieldsDO excelFields) {
        return excelFieldsDao.save(excelFields);
    }

    @Override
    public int update(ExcelFieldsDO excelFields) {
        return excelFieldsDao.update(excelFields);
    }

    @Override
    public int remove(String moduleName) {
        return excelFieldsDao.remove(moduleName);
    }

    @Override
    public int batchRemove(String[] moduleNames) {
        return excelFieldsDao.batchRemove(moduleNames);
    }

}
