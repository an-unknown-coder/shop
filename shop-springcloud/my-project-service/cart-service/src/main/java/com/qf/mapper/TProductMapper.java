package com.qf.mapper;

import com.qf.entity.TProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProductMapper {

    List<TProduct> sellectAll();

    List<TProduct> selectByPrimarykey(@Param("cid") Long cid);
}
