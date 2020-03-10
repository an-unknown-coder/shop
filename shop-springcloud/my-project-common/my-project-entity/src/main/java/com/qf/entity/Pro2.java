package com.qf.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Pro2 implements Serializable {
    private Long id;
    private String name;
    private List<Pro3> pro3List;
}
