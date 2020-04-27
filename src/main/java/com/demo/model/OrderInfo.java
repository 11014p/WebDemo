package com.demo.model;

import com.demo.enums.OrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class OrderInfo implements Serializable{
    //订单id
    private Integer orderId;
    // 用户id
    private Integer accountId;
    //产品id
    private Integer categoryId;
    //价格id
    private Integer priceId;
    //购买数量
    private Integer buyNum;
    //售价
    private Double salePrice;
    //已完成数量
    private Integer completeNum;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //订单状态
    private OrderStatus status;
    //删除标记
    private String isDel;
}