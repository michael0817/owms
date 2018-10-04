package com.bootdo.order.dao;

import com.bootdo.order.domain.ModuleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 订单模板
 *
 * @author xumx
 * @email michael0817@126.com
 * @date 2018-09-22 16:27:23
 */
@Mapper
public interface ModuleDao {

    ModuleDO get(Long moduleId);

    List<ModuleDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(ModuleDO module);

    int update(ModuleDO module);

    int remove(Long moduleId);

    int batchRemove(Long[] moduleIds);
}
