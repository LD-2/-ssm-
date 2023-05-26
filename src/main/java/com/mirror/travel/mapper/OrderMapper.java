package com.mirror.travel.mapper;

import com.mirror.travel.pojo.Order;


public interface OrderMapper {
    public int addOrder(Order order);

    public int updateStatus(Order order);

    Order findByOrderId(String orderId);

}
