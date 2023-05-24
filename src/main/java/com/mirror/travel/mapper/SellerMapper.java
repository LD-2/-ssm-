package com.mirror.travel.mapper;

import com.mirror.travel.pojo.Seller;

public interface SellerMapper {
    Seller findBySid(Integer sid);
}
