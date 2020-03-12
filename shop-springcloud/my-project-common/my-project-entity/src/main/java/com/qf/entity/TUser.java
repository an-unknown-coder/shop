package com.qf.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "t_user")
@Entity
public class TUser implements Serializable {
    @Id
    private Long uid;
    private String username;
    private String password;
    private String phone;
    private String email;
    private Integer flag;
    private Date create_time;
    private Date update_time;
}
