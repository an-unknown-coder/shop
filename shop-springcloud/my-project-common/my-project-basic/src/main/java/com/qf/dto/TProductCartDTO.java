package com.qf.dto;

import com.qf.entity.TProduct;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TProductCartDTO implements Serializable {
    private TProduct product;
    private int count;
    private Date updateTime;
}
