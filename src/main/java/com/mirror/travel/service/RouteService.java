package com.mirror.travel.service;

import com.mirror.travel.pojo.PageBean;
import com.mirror.travel.pojo.Route;

public interface RouteService {
    PageBean<Route> pageQuery(String cid, String currentPage, String size);

    Route findOne(Integer rid) throws Exception;
}
