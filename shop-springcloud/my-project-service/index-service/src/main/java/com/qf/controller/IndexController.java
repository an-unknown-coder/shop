package com.qf.controller;

import com.qf.entity.Pro1;
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
}
