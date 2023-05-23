package com.mirror.travel.service.impl;

import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.mirror.travel.mapper.UserMapper;
import com.mirror.travel.pojo.ResultInfo;
import com.mirror.travel.pojo.SmsResult;
import com.mirror.travel.pojo.User;
import com.mirror.travel.service.UserService;
import com.mirror.travel.utils.SendSmsUtil;
import com.mirror.travel.utils.ValidateCodeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    private String code = "invalid";

    @Override
    public SmsResult sendSmsCheckCode(String phone) {
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
        code = ValidateCodeUtil.generateValidateCode(6) + "";
        try {
            SendSmsResponse sendSmsResponse = SendSmsUtil.sendNoToken(phone, code);
            if(sendSmsResponse.getStatusCode()==200&&"OK".equals(sendSmsResponse.getBody().getCode())){
                resp.setCode(0);
                resp.setMsg("短信发送成功");
                resp.setResult("获取成功");
                return resp;
            }
            System.out.println(com.aliyun.teautil.Common.toJSONString(sendSmsResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.setCode(1);
        resp.setMsg("验证发送失败");
        return resp;
    }

    @Override
    public ResultInfo registerForm(User user,String check) {
        ResultInfo resp = new ResultInfo();
        //可以再添加一个 查数据库 手机号是同一个 就显示此手机号已绑定

        if("invalid".equals(code)){
            resp.setFlag(false);
            resp.setErrorMsg("验证码失效,请重新发送");
            return resp;
        }
        if(!check.equals(code)){
            resp.setFlag(false);
            resp.setErrorMsg("验证码错误");
            return resp;
        }
        User res = userMapper.selectByUserName(user.getUsername());
        if (res == null) {
            userMapper.register(user);
            resp.setFlag(true);
            resp.setErrorMsg("注册成功");
            code="invalid";
            return resp;
        }
        resp.setFlag(false);
        resp.setErrorMsg("用户已存在");
        return resp;
    }

    @Override
    public ResultInfo login(HttpServletRequest request) {
        ResultInfo resp = new ResultInfo();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String check = request.getParameter("check");
        String sessionCheck = request.getSession().getAttribute("CHECK_CODE").toString();
        if(!check.equalsIgnoreCase(sessionCheck)){
           resp.setFlag(false);
           resp.setErrorMsg("验证码错误");
           return resp;
        }
        User user = userMapper.login(username, password);
        if(user==null){
            resp.setFlag(false);
            resp.setErrorMsg("用户不存在");
            return resp;
        }
        request.getSession().setAttribute("user",user);
        resp.setFlag(true);
        resp.setErrorMsg("登录成功");
        return resp;
    }
}
