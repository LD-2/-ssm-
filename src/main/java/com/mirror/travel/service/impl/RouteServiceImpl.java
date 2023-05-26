package com.mirror.travel.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.mirror.travel.mapper.RouteImgMapper;
import com.mirror.travel.mapper.RouteMapper;
import com.mirror.travel.mapper.SellerMapper;
import com.mirror.travel.pojo.*;
import com.mirror.travel.service.RouteService;
import com.mirror.travel.utils.baidu.SearchHttpAK;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RouteServiceImpl implements RouteService {

    @Resource
    private RouteMapper routeMapper;

    @Resource
    private SellerMapper sellerMapper;

    @Resource
    private RouteImgMapper routeImgMapper;

    @Override
    public PageBean<Route> pageQuery(String cid, String currentPage, String size) {
        PageBean<Route> pageBean = new PageBean<Route>();
        int cid_int=1;
        int page_count=5;
        int current_page=1;
        if(!StringUtils.isEmpty(cid)){
            cid_int = Integer.parseInt(cid);
        }
        if(!StringUtils.isEmpty(currentPage)){
            current_page = Integer.parseInt(currentPage);
        }
        if(!StringUtils.isEmpty(size)){
            page_count = Integer.parseInt(size);
        }
        int totalCount = routeMapper.count(cid);
        int totalPages = 0;
        if (totalCount % Integer.parseInt(size) != 0) {
            totalPages = totalCount / page_count + 1;
        } else {
            totalPages = totalCount / page_count;
        }
        int start = (current_page - 1) * page_count;
        Map<String,Integer> map =new HashMap<String,Integer>();
        map.put("cid", cid_int);
        map.put("start",start);
        map.put("size",page_count);
        List<Route> routes = routeMapper.pageFindRoute(map);

        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPages);
        pageBean.setList(routes);
        pageBean.setPageSize(page_count);
        pageBean.setCurrentPage(current_page);
        return pageBean;
    }

    @Override
    public Route findOne(Integer rid) throws Exception {
        if(rid==null){
            return new Route();
        }
        //根据rid查询线路的基本信息
        Route route = routeMapper.findById(rid);
        if(route==null){
            return new Route();
        }
//        System.out.println(route);
        Seller seller = sellerMapper.findBySid(route.getSid());
        route.setSeller(seller);
//        System.out.println(route);
        List<RouteImg> routeImgs = routeImgMapper.findByRid(rid);
        route.setRouteImgList(routeImgs);
        Map params = new LinkedHashMap<String, String>();
        params.put("district_id", route.getDistrictid().toString());
        params.put("data_type", "all");
        params.put("ak", SearchHttpAK.AK);
        JSONArray jsonArray = SearchHttpAK.requestGetAK(SearchHttpAK.URL, params);
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setForecasts(jsonArray);
        route.setWeatherInfo(weatherInfo);
        return route;
    }
}
