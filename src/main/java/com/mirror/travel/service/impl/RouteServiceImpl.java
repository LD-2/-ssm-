package com.mirror.travel.service.impl;

import com.mirror.travel.mapper.RouteImgMapper;
import com.mirror.travel.mapper.RouteMapper;
import com.mirror.travel.mapper.SellerMapper;
import com.mirror.travel.pojo.PageBean;
import com.mirror.travel.pojo.Route;
import com.mirror.travel.pojo.RouteImg;
import com.mirror.travel.pojo.Seller;
import com.mirror.travel.service.RouteService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.HashMap;
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
    public Route findOne(Integer rid) {
        Route route = routeMapper.findById(rid);
//        System.out.println(route);
        Seller seller = sellerMapper.findBySid(route.getSid());
        route.setSeller(seller);
//        System.out.println(route);
        List<RouteImg> routeImgs = routeImgMapper.findByRid(rid);
        route.setRouteImgList(routeImgs);
        return route;
    }
}
