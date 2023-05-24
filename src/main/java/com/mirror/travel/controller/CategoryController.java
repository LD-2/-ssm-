package com.mirror.travel.controller;

import com.mirror.travel.pojo.Category;
import com.mirror.travel.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @ResponseBody
    @GetMapping("/findAll")
    public List<Category> findAll(){
        return categoryService.findAll();
    }

}
