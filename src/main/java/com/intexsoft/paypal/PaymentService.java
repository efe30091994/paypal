package com.intexsoft.paypal;


import java.net.URI;

public interface PaymentService {

    CreatedOrder createOrder(double totalAmount, URI returnUrl);

    void captureOrder(String orderId);
}
