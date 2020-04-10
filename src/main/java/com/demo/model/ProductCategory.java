package com.demo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ProductCategory implements Serializable{
    private Integer id;

    private Integer parentId;

    private String name;

    private String description;

    private String status;

    private String language;

    private Date createTime;

    private Date updateTime;

    private String isDel;
}