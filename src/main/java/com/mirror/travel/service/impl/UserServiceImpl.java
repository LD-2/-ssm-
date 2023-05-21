package com.mirror.travel.service.impl;

import com.mirror.travel.mapper.UserMapper;
import com.mirror.travel.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

}
