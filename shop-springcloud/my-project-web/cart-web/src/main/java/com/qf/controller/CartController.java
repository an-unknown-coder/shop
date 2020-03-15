package com.qf.controller;

import com.qf.constant.CookieConstant;
import com.qf.dto.ResultBean;
import com.qf.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("car")
public class CartController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("add/{productId}/{count}")
    @ResponseBody
    public ResultBean addCarts(@CookieValue(name = CookieConstant.USER_CART) String uuid,
                               @PathVariable Long productId,
                               @PathVariable int count,
                               HttpServletResponse response,
                               HttpServletRequest request
                               ){


        // 拿到user 对象 封装的uuid
        Object o = request.getAttribute("user");
        if (o !=null){
            // 拿到已经登录状态下的购物车  redis : user:login:userid
            TUser user = (TUser) o;
            Long userUid = user.getUid();
            restTemplate.getForObject(String.format("http://cart-service/cart/getCart/%s/%s/%s",userUid.toString(),productId,count),ResultBean.class);

        }
        //
        //----未登录状态下的购物车-----------

        //把商品添加到购物车  这个购物车是在redis 中
        if(uuid == null || "".equals(uuid)){
             uuid = UUID.randomUUID().toString();
            //返回uuid 给cookie
            Cookie cookie = new Cookie(CookieConstant.USER_CART, uuid);
            cookie.setPath("/");
            response.addCookie(cookie);

        }

        ResultBean resultBean = restTemplate.getForObject(String.format("http://cart-service/cart/getCart/%s/%s/%s", uuid, productId, count), ResultBean.class);
        return resultBean;
    }


    /**
     * 清空购物车
     */

    @RequestMapping("clean")
    @ResponseBody
    public ResultBean cleanCarts(@CookieValue(name = CookieConstant.USER_CART) String uuid,
                                 HttpServletRequest request,
                                 HttpServletResponse response){

        Object o = request.getAttribute("user");
        if (o != null){
            //--登录状态下-------------
            TUser user = (TUser) o;
            ResultBean resultBean = restTemplate.getForObject(String.format("http://cart-service/cart/del/%s", user.getUid().toString()), ResultBean.class);
            return resultBean;
        }
        //-----未登录--------
        if (uuid != null || "".equals(uuid)){
            // 删除cookie
            Cookie cookie = new Cookie(CookieConstant.USER_CART, uuid);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            ResultBean resultBean = restTemplate.getForObject(String.format("http://cart-service/cart/del/%s", uuid), ResultBean.class);
            return resultBean;
        }
            return ResultBean.error("当前用户没有购物车");
    }

    /**
     *更新购物车
     */
    @RequestMapping("update/{productId}/{count}")
    @ResponseBody
    public ResultBean updateCarts(@CookieValue(name = CookieConstant.USER_CART) String uuid,
                                  @PathVariable Long productId,
                                  @PathVariable int count,
                                  HttpServletResponse response,
                                  HttpServletRequest request){
        Object o = request.getAttribute("user");
        if (o!=null){
        //-------已登录状态下的购物车------------

        TUser user = (TUser) o;
            ResultBean resultBean = restTemplate.getForObject(String.format("http://cart-service/cart/update/%s/%s/%s", user.getUid().toString(),productId,count), ResultBean.class);
            return resultBean;
        }
        ResultBean resultBean = restTemplate.getForObject(String.format("http://cart-service/cart/update/%s/%s/%s", uuid, productId, count), ResultBean.class);
        return resultBean;
    }

    /**
     *
     *展示购物车
     */

    @RequestMapping("show")
    @ResponseBody
    public  ResultBean showCarts(@CookieValue(name = CookieConstant.USER_CART) String uuid,
                                 HttpServletRequest request){

        Object o = request.getAttribute("user");
        if (o !=null){
            // 查看登录状态下的购物车
            TUser user = (TUser) o;
            ResultBean resultBean = restTemplate.getForObject(String.format("http://cart-service/cart/show/%s", user.getUid().toString()), ResultBean.class);
            return resultBean;
        }

        // 未登录状态下的购物车
        ResultBean resultBean = restTemplate.getForObject(String.format("http://cart-service/cart/show/%s", uuid), ResultBean.class);
        return resultBean;
    }

    /**
     *
     *合并两种购物车
     */

    @RequestMapping("merge")
    @ResponseBody
    public ResultBean mergeLogin(@CookieValue(name = CookieConstant.USER_CART) String uuid,
                                 HttpServletRequest request,
                                 HttpServletResponse response){

        // 获得uuid 和userid
      TUser user = (TUser) request.getAttribute("user");


      String userId = null;
      if (user !=null){
        userId = user.getUid().toString();
      }

        Cookie cookie = new Cookie(CookieConstant.USER_CART, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        ResultBean resultBean = restTemplate.getForObject(String.format("http://cart-service/cart/merge/*s/*s", uuid, userId), ResultBean.class);
        return resultBean;
    }
}
