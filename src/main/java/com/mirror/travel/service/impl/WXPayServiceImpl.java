package com.mirror.travel.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.mirror.travel.mapper.RouteMapper;
import com.mirror.travel.pojo.Route;
import com.mirror.travel.service.WXPayService;
import com.mirror.travel.utils.PayConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class WXPayServiceImpl implements WXPayService {
    @Resource
    private RouteMapper routeMapper;
    @Override
    public Map<String, String> createNative(String out_trade_no, String money, String goods) {
        PayConfig config = new PayConfig(); //创建配置对象 获取公众号 商户号 密钥参数 因为微信API需要这几个参数
        //微信支付操作对象
        WXPay wxPay = new WXPay(config);
        Map<String,String> map = new HashMap<>();
        //商品的描述  付款的信息就是商品描述
        Route route = routeMapper.findById(Integer.parseInt(goods));
        map.put("body",route.getTitle());
        //商户的订单号
        map.put("out_trade_no",out_trade_no);
        //标价币种 fee_type  默认的货币是CNY
        map.put("fee_type","CNY");
        //标价金额 total_fee
        map.put("total_fee",money);
        ///终端IP spbill_create_ip  客户端的ip地址
        map.put("spbill_create_ip","127.0.0.1");
        //通知地址 通知完成支付的地址 notify_url
        map.put("notify_url","https://www.weixin.qq.com/wxpay/pay.php");
        //交易类型
        map.put("trade_type","NATIVE");
        //商品的id
        map.put("product_id",goods);
        try {
            Map<String, String> resp = wxPay.unifiedOrder(map);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 查询订单状态
     * @param out_trade_no
     * @return
     */
    @Override
    public Map<String, String> queryNative(String out_trade_no) {
        PayConfig config = new PayConfig();
        WXPay wxpay = new WXPay(config);
        /**
         * 封装查询订单 请求参数
         */
        Map<String, String> data = new HashMap<String, String>();
        //封住订单号
        data.put("out_trade_no", out_trade_no);

        try {
            Map<String, String> resp = wxpay.orderQuery(data);
            System.out.println(resp);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭订单
     * @param out_trade_no
     * @return
     */
    @Override
    public Map<String, String> closeNative(String out_trade_no) {
        PayConfig config = new PayConfig();
        WXPay wxpay = new WXPay(config);
        /**
         * 封装关闭订单的请求参数
         */
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", out_trade_no);//关闭订单的订单号

        try {
            Map<String, String> resp = wxpay.closeOrder(data);
            System.out.println(resp);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
