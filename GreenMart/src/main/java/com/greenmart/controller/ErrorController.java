package com.greenmart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("error")
    public String error() {
        System.out.println("error page is called");
        return "errorPage"; 
    }
}
