package com.demo.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class ProductDiscount  implements Serializable {
    private Integer discountId;

    private Integer priceId;

    private Integer minNum;

    private Integer maxNum;

    private Double rate;

    private Double promoRate;

    private String isDel;
    }