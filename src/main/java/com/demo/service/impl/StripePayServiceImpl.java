package com.demo.service.impl;

import com.demo.model.StripePayRequestVO;
import com.demo.service.StripePayService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StripePayServiceImpl implements StripePayService {
    @Override
    public String pay(StripePayRequestVO payRequestVO) {
        try {
            Stripe.apiKey = "your_apikey";

            Map<String, Object> params = new HashMap<String, Object>();
            List<String> paymentMethodTypes = new ArrayList<>();
            paymentMethodTypes.add("card");
            params.put("payment_method_types", paymentMethodTypes);
            List<Map<String, Object>> lineItems = new ArrayList<>();
            Map<String, Object> lineItem = new HashMap<>();
            lineItem.put("name", "test_name");
            lineItem.put("description", "test_desc");
            lineItem.put("amount", 100);//金额
            lineItem.put("currency", "usd");
            lineItem.put("quantity", 1);
            lineItems.add(lineItem);
            params.put("line_items", lineItems);

            String uuid = UUID.randomUUID().toString();
            params.put("client_reference_id", uuid);
            params.put("success_url", "..../paySuccess");
            params.put("cancel_url", "..../payError");

            Session session = Session.create(params);
            String sessionId = session.getId();
            return sessionId;
        } catch (StripeException e) {
            e.printStackTrace();
        }
        return null;
    }
}