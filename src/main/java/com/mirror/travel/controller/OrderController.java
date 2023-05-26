package com.mirror.travel.controller;

import com.mirror.travel.pojo.Order;
import com.mirror.travel.pojo.ResultInfo;
import com.mirror.travel.service.OrderService;
import com.mirror.travel.service.WXPayService;
import com.mirror.travel.utils.PayUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private WXPayService wxPayService;

    @ResponseBody
    @PostMapping("/add")
    public ResultInfo addOrder(Order order){
        System.out.println(order);
//        return orderService.addOrder(order);
        String out_trade_no = PayUtil.getId("01");  //生成订单号
        String payment = order.getPayment() + ""; //获取订单金额
        String money = PayUtil.getMoney(payment); //获取金额 元转分
        //使用微信API统一支付
        Map<String,String> map = wxPayService.createNative(out_trade_no,money,order.getGoods());
        if(!("SUCCESS".equals(map.get("return_code"))&&"SUCCESS".equals(map.get("result_code")))){
            //如果微信支付 生成订单失败 则返回订单创建失败
            return new ResultInfo(false,"下单失败");
        }
        order.setOrderid(out_trade_no);
        int i = orderService.addOrder(order);
        if(i<=0){
            //订单数据库保存失败
            return new ResultInfo(false,"订单保存失败");
        }
        //订单保存成功 补全返回信息
        map.put("orderId",out_trade_no);
        map.put("total_fee",money);
        return new ResultInfo(true,map,"下单成功");
    }

    @ResponseBody
    @RequestMapping("/findPayStatus")
    public ResultInfo findPayStatus(String orderId){
        Map<String,String> map = wxPayService.queryNative(orderId);
        if(map==null){
            return new ResultInfo(false,"-1","查看订单异常");
        }
        if("SUCCESS".equals(map.get("trade_state"))){
            //支付成功 修改订单状态
            int i = orderService.updateOrderStatus(orderId);
            if(i>=1){
                return new ResultInfo(true,"1","支付成功");
            }else{
                return new ResultInfo(true,"-1","订单修改异常");
            }
        }
        //查看订单是否超时
        Order order  = orderService.findById(orderId);
        Date createtime = order.getCreatetime(); //获取订单创建时间
        long time = new Date().getTime(); //获取当前时间的毫秒值
        if(time - createtime.getTime()>1000*60*5){
            wxPayService.closeNative(orderId);
            //关闭订单
            return new ResultInfo(true,"-1","订单超时，已关闭");
        }
        //既没有超时也没有关闭
        return new ResultInfo(true,"0","未支付");
    }
}
