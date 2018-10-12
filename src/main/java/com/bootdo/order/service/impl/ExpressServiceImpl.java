package com.bootdo.order.service.impl;

import com.bootdo.order.dao.ExpressDao;
import com.bootdo.order.domain.ExpressDO;
import com.bootdo.order.service.ExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class ExpressServiceImpl implements ExpressService {
    @Autowired
    private ExpressDao expressDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ExpressDO get(Date createDate) {
        return expressDao.get(createDate);
    }

    @Override
    public List<ExpressDO> list(Map<String, Object> map) {
        return expressDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return expressDao.count(map);
    }

    @Override
    public int save(ExpressDO list) {
        return expressDao.save(list);
    }

    @Override
    public int update(ExpressDO list) {
        return expressDao.update(list);
    }

    @Override
    public int remove(Date createDate) {
        return expressDao.remove(createDate);
    }

    @Override
    public List<Map<String, Object>> getGroup(Date createDate) {
        List<Map<String, Object>> expressList = jdbcTemplate.queryForList(
                "select t.*, r.module_id from express_list t left join order_list r " +
                        "on t.order_id = r.order_id and t.sku = r.sku where t.create_date = ?",
                new Object[]{createDate});
        return expressList;

    }
}
