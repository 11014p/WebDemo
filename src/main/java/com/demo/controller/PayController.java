package com.demo.controller;

import com.demo.model.StripePayRequestVO;
import com.demo.service.StripePayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PayController {
    @Autowired
    private StripePayService stripePayService;

    private static Logger logger = LoggerFactory.getLogger(PayController.class);
    /**
     * 发起支付
     * @return
     */
    @PostMapping(path = "/stripe/pay")
    public String pay(@RequestBody StripePayRequestVO stripePayRequestVO) {

        return stripePayService.pay(stripePayRequestVO);
    }


}
