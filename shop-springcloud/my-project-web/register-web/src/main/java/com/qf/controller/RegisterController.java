package com.qf.controller;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.qf.constant.RedisConstant;
import com.qf.dto.ResultBean;
import com.qf.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("email/{addr}/{password}")
    public ResultBean registerByEmail(@PathVariable String addr, @PathVariable String password) {
        MysqlxDatatypes.Scalar.String uuid = UUID.randomUUID().toString();
        String url = String.format("http://email-service/email/send/%s/%s", addr, uuid);
        ResultBean resultBean = restTemplate.getForObject(url, ResultBean.class);
        ResultBean result = null;
        if ((resultBean != null ? resultBean.getErrno() : 1) == 0) {
            String redisKey = RedisUtil.getRedisKey(RedisConstant.REGISTER_EMAIL, uuid);
            ResultBean resultBean1 = restTemplate.getForObject("http://cache-service/redis/save/" + redisKey + "/" + addr + "/" + 3600, ResultBean.class);
            if((resultBean1 != null ? resultBean1.getErrno() : 1) ==0){
                result = restTemplate.getForObject(String.format("http://regist-service/user/register/%s/%s", addr, password), ResultBean.class);
            }
        }
        if((result != null ? result.getErrno() : 1) ==0){
            return ResultBean.success("注册成功");
        }else {
            return ResultBean.error("注册失败");
        }
    }

    @RequestMapping("msg")
    public ResultBean registerByMsg(@RequestParam String uname, @RequestParam String password) {
        return null;
    }
}
