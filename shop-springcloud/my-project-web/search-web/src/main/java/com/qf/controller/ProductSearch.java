package com.qf.controller;

import com.qf.entity.TProduct;
import com.qf.service.IProductSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductSearch {
    @Autowired
    private IProductSearch productSearch;

    @RequestMapping("search")
    public List<TProduct> searchProduct(@RequestParam String keywords) {
        List<TProduct> tProducts = productSearch.searchProduct(keywords);
        System.out.println(tProducts);
        return tProducts;
    }

    @RequestMapping("initdata")
    public void initDataToSolr() {
        productSearch.initDataToSolr();
        System.out.println("数据初始化到solr完成");
    }
}
