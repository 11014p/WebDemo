package com.demo.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class ProductDiscount  implements Serializable {
    private Integer id;

    private Integer priceId;

    private Integer minNum;

    private Integer maxNum;

    private Double rate;

    private String isDel;
    }