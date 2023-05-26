package com.mirror.travel.utils;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.InputStream;

public class PayConfig implements WXPayConfig {

    //企业方公众号Id
    public static String appId = "企业方公众号Id";
    //财付通平台的商户账号
    public static String partner = "财付通平台的商户账号";
    //财付通平台的商户密钥
    public static String partnerKey = "财付通平台的商户密钥";


    @Override
    public String getAppID() {
        return appId;
    }

    @Override
    public String getMchID() {
        return partner;
    }

    @Override
    public String getKey() {
        return partnerKey;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
