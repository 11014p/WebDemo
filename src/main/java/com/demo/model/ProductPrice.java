package com.demo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProductPrice implements Serializable {
    private Integer id;

    private Integer categoryId;

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

    private Date createTime;

    private Date updateTime;

    private String isDel;
}