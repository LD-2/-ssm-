package com.mirror.travel.service;

import com.mirror.travel.pojo.ResultInfo;
import com.mirror.travel.pojo.SmsResult;
import com.mirror.travel.pojo.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    SmsResult sendSmsCheckCode(String phone);
    ResultInfo registerForm(User user,String check);

    ResultInfo login(HttpServletRequest request);
}
