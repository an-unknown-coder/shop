package com.qf.controller;

import com.qf.entity.TProduct;
import com.qf.service.IProductSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
//@RequestMapping("product")
public class ProductSearch {
    @Autowired
    private IProductSearch productSearch;

    @RequestMapping("product/search")
    public String searchProduct(@RequestParam String keywords ,Model model) {
        List<TProduct> tProducts = productSearch.searchProduct(keywords);
        model.addAttribute("tProducts",tProducts);
        System.out.println(tProducts);
        return "search";
    }

    @RequestMapping("product/initdata")
    public void initDataToSolr() {
        productSearch.initDataToSolr();
        System.out.println("加载完成!");
    }
}
