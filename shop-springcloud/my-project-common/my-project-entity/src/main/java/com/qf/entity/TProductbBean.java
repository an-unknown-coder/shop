package com.qf.entity;

import java.io.Serializable;
import java.util.Date;

public class TProductbBean implements Serializable {
    private Long pid;

    private String pname;

    private Long price;

    private Integer nums;

    private Long salePrice;

    private Long typeId;

    private Byte status;

    private String pimage;

    private Byte flag;

    private Date createTime;

    private Date updateTime;

    private Long createUser;

    private Long updateUser;

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
        this.pname = pname == null ? null : pname.trim();
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage == null ? null : pimage.trim();
    }

    public Byte getFlag() {
        return flag;
    }

    public void setFlag(Byte flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }


    public TProductbBean(Long pid, String pname, Long price, Integer nums, Long salePrice, Long typeId, Byte status, String pimage, Byte flag, Date createTime, Date updateTime, Long createUser, Long updateUser) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.nums = nums;
        this.salePrice = salePrice;
        this.typeId = typeId;
        this.status = status;
        this.pimage = pimage;
        this.flag = flag;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createUser = createUser;
        this.updateUser = updateUser;
    }

    public Integer getNums() {
        return nums;
    }

    @Override
    public String toString() {
        return "TProductbBean{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", price=" + price +
                ", nums=" + nums +
                ", salePrice=" + salePrice +
                ", typeId=" + typeId +
                ", status=" + status +
                ", pimage='" + pimage + '\'' +
                ", flag=" + flag +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createUser=" + createUser +
                ", updateUser=" + updateUser +
                '}';
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }
}
