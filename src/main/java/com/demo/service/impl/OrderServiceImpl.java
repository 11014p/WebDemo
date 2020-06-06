package com.demo.service.impl;

import com.demo.dao.mapper.*;
import com.demo.enums.OrderStatus;
import com.demo.model.*;
import com.demo.service.OrderService;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public List<OrderInfo> getOrderInfo(int accountId, int categoryId) {
        Preconditions.checkNotNull(accountId, "accountId is null.");
        Preconditions.checkNotNull(categoryId, "categoryId is null.");
        return orderInfoMapper.getOrderInfoByAccountIdCategoryId(accountId, categoryId);
    }

    @Override
    //不允许在同一时间购买多次url相同产品，必须等前一次订单完成之后再下单，否则调用外围接口有问题。
    public boolean isOrderInRunning(String url) {
        Preconditions.checkNotNull(url, "url is null.");
        List<OrderInfo> orderInfos = orderInfoMapper.getOrderInfoByUrl(url);
        if (orderInfos.isEmpty()) {
            return false;
        } else {
            List<OrderInfo> collect = orderInfos.stream()
                    .filter(orderInfo -> OrderStatus.RUNNING.equals(orderInfo.getStatus()))
                    .collect(Collectors.toList());
            if (collect.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public void saveOrderInfo(OrderInfo orderInfo) {
        Preconditions.checkNotNull(orderInfo, "orderInfo is null.");
        orderInfoMapper.insertOrderInfo(orderInfo);
        logger.info("save order success,orderinfo[{}]",orderInfo);
    }
}
