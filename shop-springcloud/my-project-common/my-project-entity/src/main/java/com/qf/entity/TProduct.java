package com.qf.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TProduct implements Serializable {
    private Long pid;
    private String pname;
    private BigDecimal price;
    private String pimage;

    public TProduct() {
    }

    public TProduct(Long pid, String pname, BigDecimal price, String pimage) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.pimage = pimage;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }
}
