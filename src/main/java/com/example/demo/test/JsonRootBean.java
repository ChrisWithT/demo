package com.example.demo.test;


import lombok.Data;

import java.util.List;


@Data
public class JsonRootBean {

    private String msg;
    private int code;
    private List<List<DataInfo>> data;

}