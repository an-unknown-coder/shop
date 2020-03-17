package com.qf.mapper;

import com.qf.entity.Pro1;
import com.qf.entity.TProduct;

import java.util.List;

public interface TypeMapper {
    List<Pro1> queryAllSort();

    List<Pro1> queryFuShiSort();

    List<TProduct> queryIndexProducts();
}
