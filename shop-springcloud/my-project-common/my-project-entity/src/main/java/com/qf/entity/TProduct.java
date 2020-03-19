package com.qf.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Table(name = "t_product")
@Entity
public class TProduct implements Serializable {
    @Id
    private Long pid;
    private String pname;
    private BigDecimal price;
    private String pimage;
    private Long typeId;
    private BigDecimal sale_price;
    private Integer monthly_sales;
    private Integer cumulative_sales;
    private Integer cumulative_evaluate;


}
