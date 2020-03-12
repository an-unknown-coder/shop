package com.qf.controller;

import com.qf.dto.ResultBean;
import com.qf.entity.TUser;
import com.qf.mapper.UserMapper;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;

@SuppressWarnings("all")
@RestController
@RequestMapping("user")
public class RegisterController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("email/{addr}/{password}")
    public ResultBean registerByEmail(@PathVariable String addr, @PathVariable String password) {
        TUser user = new TUser();
        user.setUsername(addr);
        user.setEmail(addr);
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        user.setFlag(0);
        user.setCreate_time(new Date());
        user.setUpdate_time(new Date());
        int insert = userMapper.insert(user);
        if (insert == 1) {
            return ResultBean.success("注册成功！请到邮箱接收激活链接！");
        } else {
            return ResultBean.error("注册失败！");
        }
    }

    @RequestMapping("tel/{phone}/{password}")
    public ResultBean registerByTel(@PathVariable String phone, @PathVariable String password) {
        TUser user = new TUser();
        user.setUsername(phone);
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        user.setPhone(phone);
        user.setFlag(0);
        user.setCreate_time(new Date());
        user.setUpdate_time(new Date());
        int insert = userMapper.insert(user);
        if (insert == 1) {
            return ResultBean.success("注册成功！请到邮箱接收激活链接！");
        } else {
            return ResultBean.error("注册失败！");
        }
    }

    @RequestMapping("activate/{uuid}")
    public ResultBean activate(@PathVariable String uuid) {
        try {
            String username = restTemplate.getForObject("http://cache-service/redis/get/" + uuid, String.class);
            if (username == null) {
                return ResultBean.error("激活时间已过，请重新注册！");
            }
            TUser user = new TUser();
            user.setUsername(username);
            TUser tUser = userMapper.selectOne(user);
            if (tUser.getFlag() == 1) {
                return ResultBean.error("不可重复激活");
            }
            tUser.setFlag(1);
            tUser.setUpdate_time(new Date());
            int i = userMapper.updateByPrimaryKey(tUser);
            if (i == 1) {
                return ResultBean.success("激活成功！");
            } else {
                return ResultBean.error("激活失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBean.error("激活失败！");
        }
    }
}
