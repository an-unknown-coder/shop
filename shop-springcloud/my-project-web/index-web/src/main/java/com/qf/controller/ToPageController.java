package com.qf.controller;

import com.google.gson.Gson;
import com.qf.constant.RedisConstant;
import com.qf.entity.TUser;
import com.qf.util.RedisUtil;
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
    public String toIndex(@CookieValue(value = RedisConstant.USER_LOGIN_UUID,required = false) String uuid, Model model) {
        if (uuid == null) {
            model.addAttribute("isLogin", false);
        } else {
            String redisKey = RedisUtil.getRedisKey(RedisConstant.USER_LOGIN_PRE, uuid);
            String userJson = restTemplate.getForObject("http://cache-service/redis/get/" + redisKey, String.class);
            TUser tUser = new Gson().fromJson(userJson, TUser.class);
            if (tUser == null) {
                model.addAttribute("isLogin", false);
            } else {
                model.addAttribute("isLogin", true);
                model.addAttribute("userInfo", tUser);
            }
        }
        return "index";
    }
}
