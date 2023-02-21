package com.example.demo.test;

import lombok.Data;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Data
public class DataInfo {

    private String id;
    private String dataId;
    private String deptName;
    private String deptCode;
    private String createBy;
    private String createCode;
    private Date createTime;
    private String templateName;
    private String planYear;
    private String yearSeasonKind;
    private String seasonClass;
    private String planSummaryKind;
    private String comments;
    private String submitStatus;
    private String submitHumanStatus;
    private String reasonDescription;
    private boolean openClose;
    private String openStartTime;
    private String openEndTime;
    private List<LinkedHashMap<String, String>> maps;

}