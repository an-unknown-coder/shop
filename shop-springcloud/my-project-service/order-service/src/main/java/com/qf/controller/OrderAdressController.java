package com.qf.controller;


import com.qf.dto.ResultBean;
import com.qf.entity.TOrderInventoryBean;
import com.qf.mapper.TOrderInventoryMapper;
import com.qf.service.OrderAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("orderAddress")
public class OrderAdressController {

    @Autowired
    private OrderAddress orderAddress;

    @Autowired
    private TOrderInventoryMapper tOrderInventoryMapper;


    @RequestMapping("queryAll")
    public ResultBean queryAlladd(){

        return ResultBean.success(orderAddress.queryAllAdderss(), "请求成功");
    }



    @RequestMapping("queryOneInvenTory")
    public Integer queryInventory(Integer id){
        TOrderInventoryBean bean = new TOrderInventoryBean();
        bean.setId(id);

        TOrderInventoryBean selectOne = tOrderInventoryMapper.selectOne(bean);

        Integer productTotal = selectOne.getProductTotal();


        return productTotal;
    }



}
