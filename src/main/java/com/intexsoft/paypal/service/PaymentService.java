package com.intexsoft.paypal.service;


import com.intexsoft.paypal.entity.CreatedOrder;

import java.net.URI;

public interface PaymentService {

    CreatedOrder createOrder(double totalAmount, URI returnUrl);

    void captureOrder(String orderId);
}
