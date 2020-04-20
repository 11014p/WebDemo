package com.demo.service;

import com.demo.enums.AccountStatusEnum;
import com.demo.vo.ProductCategoryVo;

import java.util.List;

public interface ProductService {

    //获取所有产品信息
    List<ProductCategoryVo> getAllProductInfo(String language);

    //获取单类产品信息
    ProductCategoryVo getProductByName(String name,String language);

    //获取商品售价
    ProductCategoryVo getProductSalePrice(int categoryId, int priceId, int buyCount, String language);
    }
