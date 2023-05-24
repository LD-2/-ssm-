package com.mirror.travel.mapper;

import com.mirror.travel.pojo.Route;

import java.util.List;
import java.util.Map;

public interface RouteMapper {
    int count(String cid);

    List<Route> pageFindRoute(Map<String,Integer> map);

    Route findById(Integer rid);
}
