package com.qf.controller;

import com.qf.constant.CookieConstant;
import com.qf.constant.RedisConstant;
import com.qf.dto.ResultBean;
import com.qf.entity.TProduct;
import com.qf.entity.UserLoginInfo;
import com.qf.mapper.TProductDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class TProductDetailsController {

    @Resource
    private TProductDetailsMapper tProductDetailsMapper;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/detail/{pid}")
    public String queryProductByPremaryKey(@PathVariable Long pid, Model model){

        TProduct product = tProductDetailsMapper.selectByPrimaryKey(pid);

        model.addAttribute("product",product);
        return "introduction";


    }

    @GetMapping("add/{product_id}")
    @ResponseBody
    public ResultBean addOne(@CookieValue(value = CookieConstant.USER_CART_UUID, required = false) String cart_uuid,
                             @CookieValue(value = RedisConstant.USER_LOGIN_UUID, required = false) String login_uuid,
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
