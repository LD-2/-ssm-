package com.mirror.travel.controller;

import com.mirror.travel.pojo.ResultInfo;
import com.mirror.travel.pojo.SmsResult;
import com.mirror.travel.pojo.User;
import com.mirror.travel.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {


    @Resource
    private UserService userService;

    //http://localhost:9090/user/sendSmsCheckCode?phone=13781877026
    @ResponseBody
    @GetMapping("/sendSmsCheckCode")
    public SmsResult sendSmsCheckCode(String phone){
        return userService.sendSmsCheckCode(phone);
    }

    @ResponseBody
    @PostMapping("/registerForm")
    public ResultInfo registerForm(User user,String check){
        return userService.registerForm(user,check);
    }

    @ResponseBody
    @PostMapping("/login")
    public ResultInfo login(HttpServletRequest request){
        return userService.login(request);
    }

    @ResponseBody
    @GetMapping("/findOne")
    public User findOne(HttpSession session){
        return (User)session.getAttribute("user");
    }

    @GetMapping("/exit")
    public String exit(HttpSession session){
//        session.invalidate();
        session.removeAttribute("user");
        return "redirect:/login.html";
    }
}
