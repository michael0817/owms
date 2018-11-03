package com.bootdo.order.service.impl;

import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;
import com.bootdo.order.dao.OrderDao;
import com.bootdo.order.domain.OrderDO;
import com.bootdo.order.domain.StockDO;
import com.bootdo.order.service.Exeption;
import com.bootdo.order.service.OrderService;
import com.bootdo.order.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private StockService stockService;

    @Autowired
    private DictService dictService;

    @Override
    public OrderDO get(String orderId) {
        return orderDao.get(orderId);
    }

    @Override
    public List<OrderDO> list(Map<String, Object> map) {
        return orderDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return orderDao.count(map);
    }

    @Override
    public int save(OrderDO list) {
        return orderDao.save(list);
    }

    @Override
    public int update(OrderDO list) {
        return orderDao.update(list);
    }

    @Override
    public int remove(String orderId) {
        return orderDao.remove(orderId);
    }

    @Override
    public int removeByDate(Date createDate) {
        return orderDao.removeByDate(createDate);
    }

    @Override
    public int batchRemove(String[] orderIds) {
        return orderDao.batchRemove(orderIds);
    }

    @Override
    @Transactional
    public int saveOrder(OrderDO orderDO) throws Exeption {
        Map params = new HashMap();
        params.put("sku",orderDO.getSku());
        List<StockDO> stockList = stockService.list(params);
        for(StockDO stockDO : stockList){
            if(orderDO.getQuantity()!= null && stockDO.getActualStocks() >= Integer.parseInt(orderDO.getQuantity())){
                stockDO.setActualStocks(stockDO.getActualStocks()-Integer.parseInt(orderDO.getQuantity()));
                stockDO.setDataDate(orderDO.getCreateDate());
                orderDO.setWarehouseCode(stockDO.getWarehouseCode());
                stockService.update(stockDO);
                break;
            }
        }
        if(orderDO.getWarehouseCode()==null || "".equals(orderDO.getWarehouseCode())) {
            for (StockDO stockDO : stockList) {
                stockDO.setAvailableFlag(0);
                stockDO.setDataDate(orderDO.getCreateDate());
                stockService.update(stockDO);
            }
        }
        return save(orderDO);
    }

}
