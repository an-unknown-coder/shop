package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ToPageController {
    @RequestMapping("toIndex")
    public String toIndex(){
        return "index";
    }
}
