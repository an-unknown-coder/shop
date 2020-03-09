package com.qf.my.project.regist.web.controller;

import com.qf.dto.ResultBean;
import com.qf.my.project.regist.web.service.IRegistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class RegistController {


    @Autowired
    private IRegistService service;

    @RequestMapping("regist")
    public ResultBean regist(@RequestParam String uname,@RequestParam String password){
        return  service.regist(uname,password);
    }


}
