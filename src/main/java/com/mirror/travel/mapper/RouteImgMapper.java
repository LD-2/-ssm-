package com.mirror.travel.mapper;

import com.mirror.travel.pojo.RouteImg;

import java.util.List;

public interface RouteImgMapper {
    List<RouteImg> findByRid(Integer rid);
}
