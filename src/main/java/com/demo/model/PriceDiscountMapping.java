package com.demo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PriceDiscountMapping implements Serializable{
    private Integer id;
    private Integer priceId;
    private Integer discountId;
    private String isDel;
}