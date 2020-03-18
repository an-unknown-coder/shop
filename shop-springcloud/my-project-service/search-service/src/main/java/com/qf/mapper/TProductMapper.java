package com.qf.mapper;

import com.qf.entity.TProduct;

import java.util.List;

public interface TProductMapper {
    List<TProduct> selectAll();
    List<TProduct> queryGoodsByTypeId(Long typeId);
}
