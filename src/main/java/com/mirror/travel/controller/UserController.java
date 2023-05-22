package com.mirror.travel.controller;

import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.mirror.travel.pojo.SmsResult;
import com.mirror.travel.utils.SendSmsUtil;
import com.mirror.travel.utils.ValidateCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    //http://localhost:9090/user/sendSmsCheckCode?phone=13781877026
    @ResponseBody
    @GetMapping("/sendSmsCheckCode")
    public SmsResult sendSmsCheckCode(String phone){
        SmsResult resp = new SmsResult();
        //后端检验手机号 此处只检验是否为空
        if(phone==null||phone.equals("")){
            resp.setCode(1);
            resp.setMsg("手机号不能为空");
            return resp;
        }
        String reges = "^1[3\\|4\\|5\\|6\\|7\\|8\\|9]\\d{9}$";
        if(!phone.matches(reges)){
            resp.setCode(1);
            resp.setMsg("手机号格式不正确");
            return resp;
        }
        //生成随机6位验证码
        String code = ValidateCodeUtil.generateValidateCode(6) + "";
        try {
            SendSmsResponse sendSmsResponse = SendSmsUtil.sendNoToken(phone, code);
            if(sendSmsResponse.getStatusCode()==200&&"OK".equals(sendSmsResponse.getBody().getCode())){
                resp.setCode(0);
                resp.setMsg("短信发送成功");
                resp.setResult(code);
                return resp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.setCode(1);
        resp.setMsg("验证发送失败");
        return resp;
    }

}
