package com.qf.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class TOrderdetail implements Serializable {

  private long id;
  private long orderId;
  private long productId;
  private double price;
  private long number;
  private String productName;
  private double fee;
  private double total0;
  private String isComment;
  private String lowStocks;
  private long score;
  private String specInfo;
  private String giftId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }


  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }


  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }


  public long getNumber() {
    return number;
  }

  public void setNumber(long number) {
    this.number = number;
  }


  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }


  public double getFee() {
    return fee;
  }

  public void setFee(double fee) {
    this.fee = fee;
  }


  public double getTotal0() {
    return total0;
  }

  public void setTotal0(double total0) {
    this.total0 = total0;
  }


  public String getIsComment() {
    return isComment;
  }

  public void setIsComment(String isComment) {
    this.isComment = isComment;
  }


  public String getLowStocks() {
    return lowStocks;
  }

  public void setLowStocks(String lowStocks) {
    this.lowStocks = lowStocks;
  }


  public long getScore() {
    return score;
  }

  public void setScore(long score) {
    this.score = score;
  }


  public String getSpecInfo() {
    return specInfo;
  }

  public void setSpecInfo(String specInfo) {
    this.specInfo = specInfo;
  }


  public String getGiftId() {
    return giftId;
  }

  public void setGiftId(String giftId) {
    this.giftId = giftId;
  }

}
