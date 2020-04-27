package com.demo.service.impl;

import com.demo.dao.mapper.*;
import com.demo.enums.AccountStatusEnum;
import com.demo.enums.OrderStatus;
import com.demo.model.*;
import com.demo.service.OrderService;
import com.demo.service.ProductService;
import com.demo.vo.ProductCategoryVo;
import com.demo.vo.ProductPriceVo;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public List<OrderInfo> getOrderInfo(int accountId, int categoryId) {
        Preconditions.checkNotNull(accountId,"accountId is null.");
        Preconditions.checkNotNull(categoryId,"categoryId is null.");
        return orderInfoMapper.getOrderInfoByAccountIdCategoryId(accountId,categoryId);
    }

    @Override
    //同一个用户不允许在同一时间购买多次相同产品，必须等前一次订单完成之后再下单，否则调用外围接口有问题。
    public boolean isOrderInRunning(int accountId, int categoryId) {
        Preconditions.checkNotNull(accountId,"accountId is null.");
        Preconditions.checkNotNull(categoryId,"categoryId is null.");
        List<OrderInfo> orderInfos = orderInfoMapper.getOrderInfoByAccountIdCategoryId(accountId, categoryId);
        if(orderInfos.isEmpty()){
            return false;
        }else{
            List<OrderInfo> collect = orderInfos.stream()
                    .filter(orderInfo -> OrderStatus.RUNNING.equals(orderInfo.getStatus()))
                    .collect(Collectors.toList());
            if(collect.isEmpty()){
                return false;
            }else{
                return true;
            }
        }
    }
}
