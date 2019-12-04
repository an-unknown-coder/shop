package com.qf.my.project.regist.service.controller;

import com.qf.dto.ResultBean;
import com.qf.my.project.regist.service.service.IRegistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class RegistController {

    @Autowired
    private IRegistService registService;

    @RequestMapping("regist")
    public ResultBean regist(@RequestParam String uname, @RequestParam String password){
        return registService.regist(uname,password);
    }


}
