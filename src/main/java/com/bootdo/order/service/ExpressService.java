package com.bootdo.order.service;

import com.bootdo.order.domain.ExpressDO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 运单详情
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-30 14:03:43
 */
public interface ExpressService {

    ExpressDO get(Date createDate);

    List<ExpressDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(ExpressDO express);

    int update(ExpressDO express);

    int remove(Date createDate);

    List<Map<String, Object>> getGroup(Date createDate);
}
