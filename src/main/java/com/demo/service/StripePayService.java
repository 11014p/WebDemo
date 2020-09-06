package com.demo.service;

import com.demo.model.StripePayRequestVO;
import org.springframework.http.server.ServerHttpResponse;

public interface StripePayService {

    String pay(StripePayRequestVO stripePayRequestVO);

}