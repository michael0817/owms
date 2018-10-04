package com.bootdo.order.service;

import com.bootdo.order.domain.ExcelFieldsDO;

import java.util.List;
import java.util.Map;

/**
 * Excel模板字段
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-26 20:15:41
 */
public interface ExcelFieldsService {

    ExcelFieldsDO get(String moduleName);

    List<ExcelFieldsDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(ExcelFieldsDO excelFields);

    int update(ExcelFieldsDO excelFields);

    int remove(String moduleName);

    int batchRemove(String[] moduleNames);
}
