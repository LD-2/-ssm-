package com.mirror.travel.service.impl;

import com.mirror.travel.mapper.OrderMapper;
import com.mirror.travel.pojo.Order;
import com.mirror.travel.pojo.ResultInfo;
import com.mirror.travel.service.OrderService;
import com.mirror.travel.utils.PayUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public Integer addOrder(Order order) {
        order.setStatus("0");
        Date date =new Date();
        order.setCreatetime(date);
        order.setUpdatetime(date);
        return orderMapper.addOrder(order);
    }

    @Override
    public int updateOrderStatus(String orderId) {
        Order order = new Order();
        order.setOrderid(orderId);
        order.setStatus("1");
        Date date =new Date();
        order.setUpdatetime(date);
        order.setCreatetime(date);
        return orderMapper.updateStatus(order);
    }

    @Override
    public Order findById(String orderId) {
        return orderMapper.findByOrderId(orderId);
    }
}
