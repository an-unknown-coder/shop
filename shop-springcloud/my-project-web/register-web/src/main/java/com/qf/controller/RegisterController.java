package com.qf.controller;

import com.qf.constant.RedisConstant;
import com.qf.dto.ResultBean;
import com.qf.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("email")
    public ResultBean registerByEmail(String addr,String password) {
        System.out.println("addr-->"+addr+",password-->"+password);
        String uuid = UUID.randomUUID().toString();
        String url = String.format("http://email-service/email/send/%s/%s", addr, uuid);
        ResultBean resultBean = restTemplate.getForObject(url, ResultBean.class);
        ResultBean result = null;
        if ((resultBean != null ? resultBean.getErrno() : 1) == 0) {
            String redisKey = RedisUtil.getRedisKey(RedisConstant.REGISTER_EMAIL, uuid);
            ResultBean resultBean1 = restTemplate.getForObject("http://cache-service/redis/save/" + redisKey + "/" + addr + "/" + 3600, ResultBean.class);
            if ((resultBean1 != null ? resultBean1.getErrno() : 1) == 0) {
                result = restTemplate.getForObject(String.format("http://register-service/user/email/%s/%s", addr, password), ResultBean.class);
            }
        }
        return result;
    }

//    @RequestMapping("msg")
//    public ResultBean registerByMsg(@RequestParam String username, @RequestParam String password) {
//        return null;
//    }

    @RequestMapping("activate/{uuid}")
    public String activate(@PathVariable String uuid) {
        ResultBean resultBean = restTemplate.getForObject("http://register-service/user/activate/" + uuid, ResultBean.class);
        return resultBean != null ? resultBean.getMessage() : "服务器开了个小差，请稍后再试";
    }
}
