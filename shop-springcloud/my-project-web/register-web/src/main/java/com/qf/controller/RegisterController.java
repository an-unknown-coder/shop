package com.qf.controller;

import com.qf.constant.RedisConstant;
import com.qf.dto.ResultBean;
import com.qf.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("email")
    public ResultBean registerByEmail(String addr,String password) {
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

    @RequestMapping("msg")
    public ResultBean registerByMsg(String phone,String password,String code) {
        String codeKey = RedisUtil.getRedisKey(RedisConstant.USER_TEL_CODE_PRE, phone);
        String result = restTemplate.getForObject("http://cache-service/redis/get/" + codeKey, String.class);
        if (result == null){
            return ResultBean.error("没有发送验证码或者验证码已过期");
        }
        if (!result.equals(code)){
            return ResultBean.error("验证码错误");
        }
        return restTemplate.getForObject("http://register-service/user/tel/" + phone + "/" + password, ResultBean.class);
    }

    @GetMapping("send/{phone}")
    public ResultBean sendCode(@PathVariable String phone,
                           HttpServletResponse response){
        String codeKey = RedisUtil.getRedisKey(RedisConstant.USER_TEL_CODE_PRE, phone);
        String result = restTemplate.getForObject("http://cache-service/redis/get/" + codeKey, String.class);
        if (result != null){
            return ResultBean.error("您的操作过于频繁");
        }
        String code = makeCode();
        ResultBean resultBean = restTemplate.getForObject("http://cache-service/redis/save/" + codeKey + "/" + code+"/60",ResultBean.class);
        assert resultBean != null;
        if (resultBean.getErrno()==0){
            Cookie cookie = new Cookie(codeKey, code);
            cookie.setPath("/");
            cookie.setMaxAge(60);
            response.addCookie(cookie);
            return ResultBean.success();
        }
        return ResultBean.error("服务器异常");
    }

    @RequestMapping("activate/{uuid}")
    public String activate(@PathVariable String uuid) {
        ResultBean resultBean = restTemplate.getForObject("http://register-service/user/activate/" + uuid, ResultBean.class);
        return resultBean != null ? resultBean.getMessage() : "服务器开了个小差，请稍后再试";
    }

    private String makeCode(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i<4;i++){
            int num = (int) (Math.random() * 10);
            stringBuilder.append(num);
        }
        return stringBuilder.toString();
    }
}
