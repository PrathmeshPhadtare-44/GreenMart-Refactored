package com.greenmart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooterController {

	@GetMapping("/track-order")
    public String trackOrder() {
        return "track-order";  
        }

    @GetMapping("/returns-policy")
    public String returnPolicy() {
        return "returns-policy";  
        }

    @GetMapping("/shipping-info")
    public String shippingInfo() {
        return "shipping-info";  
        }

    @GetMapping("/help-support")
    public String helpSupport() {
        return "help-support";  
        }
}
