package com.qf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qf.constant.CookieConstant;
import com.qf.constant.RedisConstant;
import com.qf.dto.ResultBean;
import com.qf.dto.TProductCartDTO;
import com.qf.entity.UserLoginInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CartPageController {

    @Resource
    private RestTemplate restTemplate;

    /**
     * 展示购物车
     */
    @RequestMapping("show")
    public String showCarts(@CookieValue(value = CookieConstant.USER_CART_UUID, required = false) String cart_uuid,
                            @CookieValue(value = RedisConstant.USER_LOGIN_UUID, required = false) String login_uuid,
                            Model model) {
        if (login_uuid != null) {
            UserLoginInfo loginInfo = restTemplate.getForObject("http://login-service/checkLogin/" + login_uuid, UserLoginInfo.class);
            assert loginInfo != null;
            if (loginInfo.getIsLogin()) {
                // 已登录
                Long uid = loginInfo.getUser().getUid();
                ResultBean result = restTemplate.getForObject("http://cart-service/cart/show/" + uid, ResultBean.class);
                if (result != null) {
                    List<TProductCartDTO> dtoList = transform(result);
                    model.addAttribute("productList", dtoList);
                    return "cart";
                }
            }
        }
        //未登录
        if (cart_uuid != null) {
            ResultBean resultBean = restTemplate.getForObject("http://cart-service/cart/show/" + cart_uuid, ResultBean.class);
            if (resultBean != null) {
                List<TProductCartDTO> data = transform(resultBean);
                model.addAttribute("productList", data);
            }
        }
        return "cart";
    }

    private List<TProductCartDTO> transform(ResultBean resultBean){
        List list = (List)resultBean.getData();
        ArrayList<TProductCartDTO> arrayList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object o : list) {
            TProductCartDTO tProductCartDTO = objectMapper.convertValue(o, TProductCartDTO.class);
            arrayList.add(tProductCartDTO);
        }
        return arrayList;
    }
}
