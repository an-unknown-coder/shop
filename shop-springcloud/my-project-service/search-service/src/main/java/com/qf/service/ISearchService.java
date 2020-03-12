package com.qf.service;

import com.qf.entity.TProduct;

import java.util.List;

public interface ISearchService {
    void initDataToSolr();
    List<TProduct> searchProduct(String keywords);
}
