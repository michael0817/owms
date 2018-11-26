package com.bootdo.order.service.impl;

import com.bootdo.order.dao.OrderBatchDao;
import com.bootdo.order.domain.OrderBatchDO;
import com.bootdo.order.domain.OrderDO;
import com.bootdo.order.service.OrderBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class OrderBatchServiceImpl implements OrderBatchService {
    @Autowired
    private OrderBatchDao orderBatchDao;

    @Override
    public List<OrderBatchDO> list(Map<String, Object> map) {
        return orderBatchDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return orderBatchDao.count(map);
    }

    @Override
    public int save(OrderBatchDO orderBatch) {
        return orderBatchDao.save(orderBatch);
    }

    @Override
    public int remove(Date batchDate, Integer orderBatch) {
        return orderBatchDao.remove(batchDate, orderBatch);
    }

    @Override
    public Integer getMaxBatch(Date batchDate) {
        return orderBatchDao.getMaxBatch(batchDate);
    }

    @Override
    public List<Integer> getOrderBatchNums(Date createDate) {
        return orderBatchDao.getOrderBatchNums(createDate);

    }

}
