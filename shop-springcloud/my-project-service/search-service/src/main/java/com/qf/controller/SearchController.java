package com.qf.controller;

import com.qf.entity.TProduct;
import com.qf.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
public class SearchController {
    @Autowired
    private ISearchService searchService;
    @RequestMapping("search")
    public List<TProduct> searchProduct(String keywords) {
        return searchService.searchProduct(keywords);
    }
}
