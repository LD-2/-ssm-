package com.mirror.travel.service.impl;

import com.mirror.travel.mapper.CategoryMapper;
import com.mirror.travel.pojo.Category;
import com.mirror.travel.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }
}
