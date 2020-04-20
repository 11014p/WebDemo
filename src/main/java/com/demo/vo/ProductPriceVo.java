package com.demo.vo;

import com.demo.model.ProductDiscount;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductPriceVo implements Serializable{
    private Integer priceId;

    private String name;

    private String displayName;

    private String language;

    private Double cpm;

    private String currency;

    private Integer minNum;

    private Integer maxNum;

    private String descCommon;

    private String descNoLogin;

    private String descLogin;

    private String descAgent;

    private Double agentDiscount;

    private double salePrice;

    private List<ProductDiscount> discount= Lists.newArrayList();
}
