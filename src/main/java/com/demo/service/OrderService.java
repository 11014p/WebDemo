package com.demo.service;

import com.demo.model.OrderInfo;
import com.demo.vo.ProductCategoryVo;

import java.util.List;

/**
 * 订单业务
 */
public interface OrderService {

    //获取当前用户指定产品订单
    List<OrderInfo> getOrderInfo(int accountId,int categoryId);

    boolean isOrderInRunning(int accountId,int categoryId);
    }
