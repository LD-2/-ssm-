package com.mirror.travel.service;

import com.mirror.travel.pojo.Order;


public interface OrderService {
    public Integer addOrder(Order order);

    int updateOrderStatus(String orderId);

    Order findById(String orderId);
}
