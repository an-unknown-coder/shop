package com.qf.controller;

import com.qf.constant.CookieConstant;
import com.qf.constant.RedisConstant;
import com.qf.dto.ResultBean;
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
    public String toIndex(@CookieValue(value = RedisConstant.USER_LOGIN_UUID, required = false) String login_uuid,
                          @CookieValue(value = CookieConstant.USER_CART_UUID, required = false) String cart_uuid,
                          Model model) {
        UserLoginInfo loginInfo = restTemplate.getForObject("http://login-service/checkLogin/" + login_uuid, UserLoginInfo.class);
        model.addAttribute("loginInfo", loginInfo);
        assert loginInfo != null;
        //如果登录成功
        if (loginInfo.getIsLogin()) {
            //如果游客状态有购物车，则合并购物车
            if (cart_uuid!=null&&!"".equals(cart_uuid)){
                Long uid = loginInfo.getUser().getUid();
                restTemplate.getForObject("http://cart-web/car/merge/" + cart_uuid + "/" + uid, ResultBean.class);
            }
        }


        return "index";
    }
}
