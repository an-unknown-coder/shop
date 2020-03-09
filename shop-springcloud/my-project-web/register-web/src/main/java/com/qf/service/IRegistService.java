package com.qf.service;

import com.qf.dto.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("regist-service")
public interface IRegistService {


    @RequestMapping("user/regist")
    ResultBean regist(@RequestParam String uname,@RequestParam String password);
}
