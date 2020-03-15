package com.qf.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginInfo implements Serializable {
    private Boolean isLogin;
    private TUser user;
}
