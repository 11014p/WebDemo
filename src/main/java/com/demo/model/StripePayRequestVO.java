package com.demo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class StripePayRequestVO implements Serializable{
    /**
     * 支付类型
     */
    private String type;

    /**
     * Stripe支付token
     */
    private String token;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 套餐ID
     */
    private String productId;


    private String scene;
}
