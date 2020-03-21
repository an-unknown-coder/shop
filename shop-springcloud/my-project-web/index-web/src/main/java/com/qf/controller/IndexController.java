package com.qf.controller;

import com.qf.constant.CookieConstant;
import com.qf.constant.RedisConstant;
import com.qf.dto.ResultBean;
import com.qf.dto.SortAndProductsDTO;
import com.qf.entity.Pro1;
import com.qf.entity.UserLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("sort")
    public List<Pro1> sort() {
        List<Pro1> result = restTemplate.getForObject("http://index-service/index/sort", List.class);
        return result;
    }

    @GetMapping("products")
    public SortAndProductsDTO queryIndexProducts() {
        return restTemplate.getForObject("http://index-service/index/products", SortAndProductsDTO.class);
    }

    @GetMapping("add/{product_id}")
    public ResultBean addOne(@CookieValue(value = CookieConstant.USER_CART_UUID, required = false) String cart_uuid,
                             @CookieValue(value = CookieConstant.USER_LOGIN_UUID, required = false) String login_uuid,
                             @PathVariable Long product_id,
                             HttpServletResponse response
    ) {
        if (login_uuid != null) {
            UserLoginInfo loginInfo = restTemplate.getForObject("http://login-service/checkLogin/" + login_uuid, UserLoginInfo.class);
            assert loginInfo != null;
            if (loginInfo.getIsLogin()) {
                // 已登录
                Long uid = loginInfo.getUser().getUid();
                return restTemplate.getForObject("http://cart-service/cart/getCart/" + uid + "/" + product_id + "/1" , ResultBean.class);
            }
        }
        //未登录
        Cookie cookie = null;
        if (cart_uuid == null || "".equals(cart_uuid)) {
            cart_uuid = UUID.randomUUID().toString();
            cookie = new Cookie(CookieConstant.USER_CART_UUID, cart_uuid);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return restTemplate.getForObject("http://cart-service/cart/getCart/" + cart_uuid + "/" + product_id + "/1" , ResultBean.class);
    }
}
