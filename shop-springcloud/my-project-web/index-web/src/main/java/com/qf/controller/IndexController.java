package com.qf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qf.config.SerializableCookie;
import com.qf.constant.CookieConstant;
import com.qf.dto.ResultBean;
import com.qf.dto.SortAndProductsDTO;
import com.qf.entity.Pro1;
import com.qf.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public SortAndProductsDTO queryIndexProducts(){
        return restTemplate.getForObject("http://index-service/index/products", SortAndProductsDTO.class);
    }

    @GetMapping("add/{product_id}")
    public ResultBean addOne(@PathVariable Long product_id,
                             HttpServletResponse response){
        ResultBean forObject = restTemplate.getForObject("http://cart-web/car/add/" + product_id + "/1", ResultBean.class);
        assert forObject != null;
        Object temp = forObject.getTemp();
        System.out.println("111111111---------->"+temp);
        if (temp!=null){
            Cookie cookie = new Cookie(CookieConstant.USER_CART_UUID, (String) temp);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return forObject;
    }
}
