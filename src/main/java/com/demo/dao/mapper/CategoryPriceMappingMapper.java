package com.demo.dao.mapper;

import com.demo.model.CategoryPriceMapping;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryPriceMappingMapper {
    @Select("SELECT * FROM category_price_mapping where IS_DEL='N'")
    //查询所有产品价格映射数据
    List<CategoryPriceMapping> getAllCategoryPriceMapping();
}
