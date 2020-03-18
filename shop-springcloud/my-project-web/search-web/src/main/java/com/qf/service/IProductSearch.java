package com.qf.service;

import com.qf.entity.TProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "search-service")
public interface IProductSearch {
    @RequestMapping("product/search")
    List<TProduct> searchProduct(@RequestParam String keywords);

    @RequestMapping("product/initdata")
    void initDataToSolr();

    @RequestMapping("product/query")
    List<TProduct> queryGoodsByTypeId(@RequestParam Long typeId);
}
