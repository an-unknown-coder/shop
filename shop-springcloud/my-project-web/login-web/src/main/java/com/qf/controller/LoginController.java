package com.qf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.qf.dto.ResultBean;
import com.qf.entity.TUser;
import com.qf.util.RedisUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;

import static com.qf.constant.CookieConstant.USER_LOGIN_UUID;
import static com.qf.constant.RedisConstant.USER_LOGIN_PRE;

@RestController
public class LoginController {

    @Resource
    private RestTemplate restTemplate;

    @PostMapping("login")
    public ResultBean login(String username, String password, HttpServletResponse response) {
        ResultBean resultBean = restTemplate.getForObject("http://login-service/login/" + username + "/" + password, ResultBean.class);
        if ((resultBean != null ? resultBean.getErrno() : 1) == 0) {
            String uuid = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(USER_LOGIN_UUID, uuid);
            response.addCookie(cookie);
            ObjectMapper mapper = new ObjectMapper();
            TUser user = mapper.convertValue(resultBean.getData(), TUser.class);
            String value = new Gson().toJson(user);
            String key = RedisUtil.getRedisKey(USER_LOGIN_PRE, uuid);
            long time = 40000L;
            HashMap<String, Object> map = new HashMap<>();
            map.put("key", key);
            map.put("value", value);
            map.put("time", time);
            ResultBean result = restTemplate.getForObject("http://cache-service/redis/save/{key}/{value}/{time}", ResultBean.class, map);
            if ((result != null ? result.getErrno() : 1) == 0) {
                return ResultBean.success();
            } else {
                return ResultBean.error("服务器异常，请稍后再试！");
            }
        } else {
            return resultBean;
        }
    }
}
