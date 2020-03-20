package com.qf.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_product_inventory")
public class TOrderInventoryBean  implements Serializable {

    private Integer id;

    private Integer productTotal;

    public TOrderInventoryBean() {
    }

    public TOrderInventoryBean(Integer id, Integer productTotal) {
        this.id = id;
        this.productTotal = productTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(Integer productTotal) {
        this.productTotal = productTotal;
    }

    @Override
    public String toString() {
        return "TOrderInventoryBean{" +
                "id=" + id +
                ", productTotal=" + productTotal +
                '}';
    }
}
