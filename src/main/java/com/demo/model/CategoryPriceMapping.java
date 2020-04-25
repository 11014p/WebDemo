package com.demo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CategoryPriceMapping implements Serializable{
    private Integer id;
    private Integer categoryId;
    private Integer priceId;
    private String isDel;
}