package com.qf.controller;

import com.qf.constant.CookieConstant;
import com.qf.constant.RedisConstant;
import com.qf.dto.ResultBean;
import com.qf.entity.UserLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("car")
public class CartController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     *添加商品
     */
    @CrossOrigin
    @RequestMapping("add/{productId}/{count}")
    public ResultBean addCarts(@CookieValue(value = CookieConstant.USER_CART_UUID, required = false) String cart_uuid,
                               @CookieValue(value = RedisConstant.USER_LOGIN_UUID, required = false) String login_uuid,
                               @PathVariable Long productId,
                               @PathVariable int count,
                               HttpServletResponse response
    ) {
        if (login_uuid != null) {
            UserLoginInfo loginInfo = restTemplate.getForObject("http://login-service/checkLogin/" + login_uuid, UserLoginInfo.class);
            assert loginInfo != null;
            if (loginInfo.getIsLogin()) {
                // 已登录
                Long uid = loginInfo.getUser().getUid();
                return restTemplate.getForObject("http://cart-service/cart/getCart/" + uid + "/" + productId + "/" + count, ResultBean.class);
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
        ResultBean resultBean = restTemplate.getForObject("http://cart-service/cart/getCart/" + cart_uuid + "/" + productId + "/" + count, ResultBean.class);
        if (cookie!=null){
            assert resultBean != null;
            resultBean.setTemp(cart_uuid);
        }
        return resultBean;
    }


    /**
     * 清空购物车
     */

    @RequestMapping("clean")
    public ResultBean cleanCarts(@CookieValue(value = CookieConstant.USER_CART_UUID, required = false) String cart_uuid,
                                 @CookieValue(value = RedisConstant.USER_LOGIN_UUID, required = false) String login_uuid,
                                 HttpServletResponse response
    ) {
        if (login_uuid != null) {
            UserLoginInfo loginInfo = restTemplate.getForObject("http://login-service/checkLogin/" + login_uuid, UserLoginInfo.class);
            assert loginInfo != null;
            if (loginInfo.getIsLogin()) {
                // 已登录
                Long uid = loginInfo.getUser().getUid();
                return restTemplate.getForObject("http://cart-service/cart/del/" + uid, ResultBean.class);
            }
        }

        //未登录
        if (cart_uuid != null) {
            // 删除cookie
            Cookie cookie = new Cookie(CookieConstant.USER_CART_UUID, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            return restTemplate.getForObject("http://cart-service/cart/del/" + cart_uuid, ResultBean.class);
        }

        return ResultBean.error("当前用户没有购物车");
    }

    /**
     * 更新购物车
     */
    @RequestMapping("update/{productId}/{count}")
    public ResultBean updateCarts(@CookieValue(value = CookieConstant.USER_CART_UUID, required = false) String cart_uuid,
                                  @CookieValue(value = RedisConstant.USER_LOGIN_UUID, required = false) String login_uuid,
                                  @PathVariable Long productId,
                                  @PathVariable int count
    ) {
        if (login_uuid != null) {
            UserLoginInfo loginInfo = restTemplate.getForObject("http://login-service/checkLogin/" + login_uuid, UserLoginInfo.class);
            assert loginInfo != null;
            if (loginInfo.getIsLogin()) {
                // 已登录
                Long uid = loginInfo.getUser().getUid();
                return restTemplate.getForObject("http://cart-service/cart/update/" + uid + "/" + productId + "/" + count, ResultBean.class);
            }
        }
        //未登录
        return restTemplate.getForObject("http://cart-service/cart/update/" + cart_uuid + "/" + productId + "/" + count, ResultBean.class);
    }

    /**
     * 合并两种购物车
     */
    @RequestMapping("merge/{uuid}/{uid}")
    public ResultBean mergeLogin(@PathVariable String uuid, @PathVariable Long uid) {
        return restTemplate.getForObject(String.format("http://cart-service/cart/merge/%s/%s", uuid, uid), ResultBean.class);
    }
}
