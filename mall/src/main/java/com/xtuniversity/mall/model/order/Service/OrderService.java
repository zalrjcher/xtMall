package com.xtuniversity.mall.model.order.Service;

import com.xtuniversity.mall.model.base.BaseService;
import com.xtuniversity.mall.model.order.dao.IOrderDao;
import com.xtuniversity.mall.model.order.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zlj
 * @introduce
 */
@Service
public class OrderService extends BaseService {
    @Autowired
    @Qualifier(value = "orderDao")
    IOrderDao orderDao;
    public Order getOrderById(long id) {
        return orderDao.findById(id);
    }

    public void updateOrder(Order order) {
        orderDao.update(order);
    }

    public void saveOrder(Order order) {
        orderDao.save(order);
    }

    public List<Order> getOrderByUserId(long userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        return orderDao.findAllByParam(map,new LinkedHashMap<>());
    }

    public List<Order> getOrderByUserIdAndCommodityId(long userId, long commodityId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("commodityId",commodityId);
        return  orderDao.findAllByParam(map,new LinkedHashMap<>());
    }

    public List<Order> getOrderByUserIdAndState(long userId, int state) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("state",state);
        return  orderDao.findAllByParam(map,new LinkedHashMap<>());
    }
}
