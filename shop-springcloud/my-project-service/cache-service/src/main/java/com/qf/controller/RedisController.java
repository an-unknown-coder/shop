package com.qf.controller;

import com.qf.constant.RedisConstant;
import com.qf.dto.ResultBean;
import com.qf.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("save/{key}/{value}/{time}")
    public ResultBean save(@PathVariable String key, @PathVariable String value, @PathVariable Long time) {
        try{
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        }catch (Exception e){
            return ResultBean.error("缓存失败");
        }
        return ResultBean.success(key+"成功存入redis.");
    }

    @GetMapping("get/{key}")
    public String get(@PathVariable String key){
         return key==null?null:redisTemplate.opsForValue().get(key);
    }
}
