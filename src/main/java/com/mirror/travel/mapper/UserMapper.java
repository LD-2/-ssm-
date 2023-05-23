package com.mirror.travel.mapper;

import com.mirror.travel.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User selectByUserName(String userName);

    int register(User user);

    User login(@Param("username")String username,@Param("password")String password);
}
