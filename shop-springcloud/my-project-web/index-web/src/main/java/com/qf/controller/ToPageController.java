package com.qf.controller;

import com.qf.constant.RedisConstant;
import com.qf.entity.UserLoginInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Controller
public class ToPageController {

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("toIndex")
    public String toIndex(@CookieValue(value = RedisConstant.USER_LOGIN_UUID, required = false) String uuid, Model model) {
        UserLoginInfo loginInfo = restTemplate.getForObject("http://login-service/checkLogin/" + uuid, UserLoginInfo.class);
        model.addAttribute("loginInfo", loginInfo);
        return "index";
    }
}
