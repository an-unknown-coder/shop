package com.qf.controller;

import com.qf.dto.SortAndProductsDTO;
import com.qf.entity.Pro1;
import com.qf.entity.TProduct;
import com.qf.mapper.TypeMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("index")
public class IndexController {

    @Resource
    private TypeMapper typeMapper;

    @GetMapping("sort")
    public List<Pro1> sort(){
        return typeMapper.queryAllSort();
    }

    @RequestMapping("products")
    public SortAndProductsDTO productsDTOForIndex(){
        SortAndProductsDTO sortAndProductsDTO = new SortAndProductsDTO();
        List<Pro1> pro1s = typeMapper.queryFuShiSort();
        List<TProduct> tProducts = typeMapper.queryIndexProducts();
        if (pro1s.size()==1){
            sortAndProductsDTO.setPro1(pro1s.get(0));
        }
        sortAndProductsDTO.setProducts(tProducts);
        return sortAndProductsDTO;
    }
}
