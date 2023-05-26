package com.mirror.travel.service;

import java.util.Map;

public interface WXPayService {
    //调用API统一下单 生成支付方法
    Map<String, String> createNative(String out_trade_no, String money, String goods);

    Map<String, String> queryNative(String orderId);

    Map<String, String> closeNative(String out_trade_no);
}
