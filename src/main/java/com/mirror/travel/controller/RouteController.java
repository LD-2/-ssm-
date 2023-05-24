package com.mirror.travel.controller;

import com.mirror.travel.pojo.PageBean;
import com.mirror.travel.pojo.Route;
import com.mirror.travel.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/route")
public class RouteController {

    @Resource
    private RouteService routeService;

    @ResponseBody
    @GetMapping("/pageQuery")
    public PageBean<Route> pageQuery(String cid, String currentPage, String size){
//        System.out.println("cid: "+cid);
//        System.out.println("currentPage: "+currentPage);
//        System.out.println("size: "+size);
//        System.out.println("-------------------cid: "+cid);
//        System.out.println();
//        System.out.println();
        return routeService.pageQuery(cid,currentPage,size);
    }

    @ResponseBody
    @GetMapping("/findOne")
    public Route findOne(String rid){
        return routeService.findOne(Integer.parseInt(rid));
    }
}
