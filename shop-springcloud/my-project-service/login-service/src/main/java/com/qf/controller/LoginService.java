package com.qf.controller;

import com.qf.dto.ResultBean;
import com.qf.entity.TUser;
import com.qf.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class LoginService {

    @Resource
    private UserMapper userMapper;

    @RequestMapping("login/{username}/{password}")
    public ResultBean login(@PathVariable String username,@PathVariable String password){
        String psd = DigestUtils.md5DigestAsHex(password.getBytes());
        TUser user = new TUser();
        user.setUsername(username);
        user.setPassword(psd);
        List<TUser> users = userMapper.select(user);
        if (users!=null&&users.size()==1){
            TUser tUser = users.get(0);
            if (tUser.getFlag()==0){
                return ResultBean.error("您已注册，但还未激活!");
            }else{
                return ResultBean.success("Successfully login!");
            }
        }else {
            return ResultBean.error("用户名或密码错误!");
        }
    }
}
