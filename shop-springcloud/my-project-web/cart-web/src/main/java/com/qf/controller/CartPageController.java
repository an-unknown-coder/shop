package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartPageController {

    @RequestMapping("page")
    public String getPage(){
        return "shopcart";
    }
}
