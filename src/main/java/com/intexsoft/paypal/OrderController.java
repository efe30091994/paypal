package com.intexsoft.paypal;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final PaymentService paymentService;

    public OrderController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    private String orderId = "";

    @GetMapping
    public String orderPage(Model model){
        model.addAttribute("orderId", orderId);
        return "order";
    }

    @GetMapping("/capture")
    public String captureOrder(@RequestParam String token){
        orderId = token;
        paymentService.captureOrder(orderId);
        return "redirect:/orders";
    }

    @PostMapping
    public String placeOrder(@RequestParam double totalAmount, HttpServletRequest request){
        final URI returnUrl = buildReturnUrl(request);
        CreatedOrder createdOrder = paymentService.createOrder(totalAmount, returnUrl);

        return "redirect:"+createdOrder.getApprovalLink();
    }

    private URI buildReturnUrl(HttpServletRequest request){
        try {
            URI requestUri = URI.create(request.getRequestURL().toString());
            return new URI(requestUri.getScheme(),
            requestUri.getUserInfo(),
                    requestUri.getHost(),
                    requestUri.getPort(),
                    "/orders/capture",
                    null, null);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
