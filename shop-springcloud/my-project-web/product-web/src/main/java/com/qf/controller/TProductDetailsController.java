package com.qf.controller;

import com.qf.entity.TProduct;
import com.qf.mapper.TProductDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

@Controller
public class TProductDetailsController {

    @Resource
    private TProductDetailsMapper tProductDetailsMapper;

    @GetMapping("/detail/{pid}")
    public String queryProductByPremaryKey(@PathVariable Long pid, Model model){

        TProduct product = tProductDetailsMapper.selectByPrimaryKey(pid);

        model.addAttribute("product",product);
        return "introduction";


    }
}
