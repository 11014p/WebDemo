package com.demo.dao.mapper;

import com.demo.model.PriceDiscountMapping;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PriceDiscountMappingMapper {
    @Select("SELECT * FROM price_discount_mapping where IS_DEL='N'")
    //查询所有价格折扣映射数据
    List<PriceDiscountMapping> getAllPriceDiscountMapping();
}
