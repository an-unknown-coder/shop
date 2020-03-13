package com.qf.controller;

import com.qf.dto.ResultBean;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginService {
    @RequestMapping("login/{username}/{password}")
    public ResultBean login(@PathVariable String username,@PathVariable String password){
        String psd = DigestUtils.md5DigestAsHex(password.getBytes());
        // TODO...
        return ResultBean.success("Successfully login!");
    }
}
