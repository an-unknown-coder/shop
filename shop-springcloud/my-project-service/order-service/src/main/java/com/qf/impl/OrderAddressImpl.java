package com.qf.impl;

import com.qf.entity.TOrderAddress;
import com.qf.mapper.TOrderAddressMapper;
import com.qf.service.OrderAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;





@Service
public class OrderAddressImpl implements OrderAddress {

    @Autowired
    private TOrderAddressMapper tOrderAddressMapper;


    @Override
    public List<TOrderAddress> queryAllAdderss() {
        List<TOrderAddress> tOrderAddresses = tOrderAddressMapper.selectAll();

        return tOrderAddresses;
    }
}
