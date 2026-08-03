package com.canteen.management.service.impl;

import com.canteen.management.dto.CreateOrderResponse;
import com.canteen.management.service.RazorpayService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayServiceImpl implements RazorpayService {

    @Autowired
    private RazorpayClient razorpayClient;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Override
    public CreateOrderResponse createOrder(Double amount) throws Exception {

        JSONObject options = new JSONObject();

        options.put("amount", (int) (amount * 100));
        options.put("currency", "INR");
        options.put("receipt", "receipt_" + System.currentTimeMillis());

        Order order = razorpayClient.orders.create(options);

        return new CreateOrderResponse(
                order.get("id"),
                keyId,
                amount,
                "INR"
        );
    }
}