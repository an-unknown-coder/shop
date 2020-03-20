package com.sample;


import lombok.Data;

@Data
public class TOrder {

  private long id;
  private String account;
  private long payType;
  private long carry;
  private double rebate;
  private java.sql.Timestamp createdate;
  private String status;
  private String refundStatus;
  private double amount;
  private double fee;
  private double ptotal;
  private long quantity;
  private String paystatus;
  private String updateAmount;
  private String expressCode;
  private String expressName;
  private String otherRequirement;
  private String remark;
  private String expressNo;
  private String expressCompanyName;
  private String lowStocks;
  private String confirmSendProductRemark;
  private String closedComment;
  private long score;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }


  public long getPayType() {
    return payType;
  }

  public void setPayType(long payType) {
    this.payType = payType;
  }


  public long getCarry() {
    return carry;
  }

  public void setCarry(long carry) {
    this.carry = carry;
  }


  public double getRebate() {
    return rebate;
  }

  public void setRebate(double rebate) {
    this.rebate = rebate;
  }


  public java.sql.Timestamp getCreatedate() {
    return createdate;
  }

  public void setCreatedate(java.sql.Timestamp createdate) {
    this.createdate = createdate;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public String getRefundStatus() {
    return refundStatus;
  }

  public void setRefundStatus(String refundStatus) {
    this.refundStatus = refundStatus;
  }


  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }


  public double getFee() {
    return fee;
  }

  public void setFee(double fee) {
    this.fee = fee;
  }


  public double getPtotal() {
    return ptotal;
  }

  public void setPtotal(double ptotal) {
    this.ptotal = ptotal;
  }


  public long getQuantity() {
    return quantity;
  }

  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }


  public String getPaystatus() {
    return paystatus;
  }

  public void setPaystatus(String paystatus) {
    this.paystatus = paystatus;
  }


  public String getUpdateAmount() {
    return updateAmount;
  }

  public void setUpdateAmount(String updateAmount) {
    this.updateAmount = updateAmount;
  }


  public String getExpressCode() {
    return expressCode;
  }

  public void setExpressCode(String expressCode) {
    this.expressCode = expressCode;
  }


  public String getExpressName() {
    return expressName;
  }

  public void setExpressName(String expressName) {
    this.expressName = expressName;
  }


  public String getOtherRequirement() {
    return otherRequirement;
  }

  public void setOtherRequirement(String otherRequirement) {
    this.otherRequirement = otherRequirement;
  }


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }


  public String getExpressNo() {
    return expressNo;
  }

  public void setExpressNo(String expressNo) {
    this.expressNo = expressNo;
  }


  public String getExpressCompanyName() {
    return expressCompanyName;
  }

  public void setExpressCompanyName(String expressCompanyName) {
    this.expressCompanyName = expressCompanyName;
  }


  public String getLowStocks() {
    return lowStocks;
  }

  public void setLowStocks(String lowStocks) {
    this.lowStocks = lowStocks;
  }


  public String getConfirmSendProductRemark() {
    return confirmSendProductRemark;
  }

  public void setConfirmSendProductRemark(String confirmSendProductRemark) {
    this.confirmSendProductRemark = confirmSendProductRemark;
  }


  public String getClosedComment() {
    return closedComment;
  }

  public void setClosedComment(String closedComment) {
    this.closedComment = closedComment;
  }


  public long getScore() {
    return score;
  }

  public void setScore(long score) {
    this.score = score;
  }

}
