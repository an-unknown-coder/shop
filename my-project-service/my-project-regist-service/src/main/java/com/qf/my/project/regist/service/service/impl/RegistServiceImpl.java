package com.qf.my.project.regist.service.service.impl;

import com.qf.dto.ResultBean;
import com.qf.entity.TUser;
import com.qf.mapper.TUserMapper;
import com.qf.my.project.regist.service.service.IRegistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistServiceImpl implements IRegistService {

    @Autowired
    TUserMapper mapper;

    @Override
    public ResultBean regist(String uname, String password) {
        TUser user = new TUser();
        user.setUname(uname);
        user.setPassword(password);
        mapper.insert(user);
        return ResultBean.success("注册成功");
    }
}
