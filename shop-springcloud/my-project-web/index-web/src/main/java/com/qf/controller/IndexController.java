package com.qf.controller;

import com.qf.entity.Pro1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
    /**
     * 下面是redis缓存服务的远程调用的测试案例
     */
//    @GetMapping("save")
//    public ResultBean test() {
//        return restTemplate.getForObject("http://cache-service/redis/save/wqwq/lovelzh/30", ResultBean.class);
//    }
//
//    @GetMapping("get")
//    public String test2() {
//        return restTemplate.getForObject("http://cache-service/redis/get/wqwq", String.class);
//    }
}
