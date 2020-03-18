package com.qf.dto;

import com.qf.entity.Pro1;
import com.qf.entity.TProduct;
import lombok.Data;

import java.util.List;

@Data
public class SortAndProductsDTO {
    private List<TProduct> products;
    private Pro1 pro1;
}
