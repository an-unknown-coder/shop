package com.qf.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class CartInfo implements Serializable {
    static final java.text.DecimalFormat df =new   java.text.DecimalFormat("#.00");

    private List<TProductbBean> productList;// 购物车中商品列表
    //	private String productTotal;//商品总金额
    private String amount;// 合计总金额，也就是用户最终需要支付的金额
    private String defaultAddessID;//用户的默认地址ID

    public List<TProductbBean> getProductList() {
        if(productList==null){
            productList = new LinkedList<TProductbBean>();
        }
        return productList;
    }

    public void setProductList(List<TProductbBean> productList) {
        this.productList = productList;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }



    /**
     * 购物车汇总计算总金额
     * @return
     */
    public void totalCacl(){
        double _amount = 0;
        for(int i=0;i<getProductList().size();i++){
            TProductbBean p = getProductList().get(i);

            _amount=p.getPrice()*p.getNums();


        }

        if(_amount!=0){
            this.amount = df.format(_amount);
        }else{
            this.amount = "0.00";
        }
    }



    public String getDefaultAddessID() {
        return defaultAddessID;
    }

    public void setDefaultAddessID(String defaultAddessID) {
        this.defaultAddessID = defaultAddessID;
    }

    @Override
    public String toString() {
        return "CartInfo{" +
                "productList=" + productList +
                ", amount='" + amount + '\'' +
                ", defaultAddessID='" + defaultAddessID + '\'' +
                '}';
    }
}

