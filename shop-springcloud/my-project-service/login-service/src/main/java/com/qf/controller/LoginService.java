package com.qf.controller;

import com.google.gson.Gson;
import com.qf.constant.RedisConstant;
import com.qf.dto.ResultBean;
import com.qf.entity.TUser;
import com.qf.entity.UserLoginInfo;
import com.qf.mapper.UserMapper;
import com.qf.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class LoginService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RestTemplate restTemplate;

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
                ResultBean success = ResultBean.success("Successfully login!");
                success.setData(tUser);
                return success;
            }
        }else {
            return ResultBean.error("用户名或密码错误!");
        }
    }

    @RequestMapping("checkLogin/{uuid}")
    public UserLoginInfo checkLogin(@PathVariable String uuid){
        UserLoginInfo loginInfo = new UserLoginInfo();
        if (uuid == null) {
            loginInfo.setIsLogin(false);
        } else {
            String redisKey = RedisUtil.getRedisKey(RedisConstant.USER_LOGIN_PRE, uuid);
            String userJson = restTemplate.getForObject("http://cache-service/redis/get/" + redisKey, String.class);
            TUser tUser = new Gson().fromJson(userJson, TUser.class);
            if (tUser == null) {
                loginInfo.setIsLogin(false);
            } else {
                loginInfo.setIsLogin(true);
                loginInfo.setUser(tUser);
            }
        }
        return loginInfo;
    }
}
