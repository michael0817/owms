package com.bootdo.order.dao;

import com.bootdo.order.domain.ExpressDO;
import org.apache.ibatis.annotations.Mapper;

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
@Mapper
public interface ExpressDao {

    ExpressDO get(Date createDate);

    List<ExpressDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(ExpressDO list);

    int update(ExpressDO list);

    int remove(Date createDate);

    int batchRemove(Date[] createDate);
}
