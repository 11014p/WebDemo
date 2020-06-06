package com.demo.vo;

import com.demo.enums.AccountRole;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class ProductCategoryVo implements Serializable {
    private Integer categoryId;

    private Integer parentId;

    private String name;

    private String description;

    private AccountRole status;

    private String language;

    private List<ProductPriceVo> product= Lists.newArrayList();

    private List<ProductCategoryVo> children= Lists.newArrayList();
}
