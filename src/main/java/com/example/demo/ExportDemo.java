package com.example.demo;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.cell.CellUtil;
import com.example.demo.test.DataInfo;
import com.example.demo.test.JsonRootBean;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExportDemo {


    public static void main(String[] args) {

        //这部分是取数据的，忽略
        String json = FileUtil.readString("data.json", Charset.defaultCharset());
        JsonRootBean bean = JSONUtil.toBean(json, JsonRootBean.class);
        List<List<DataInfo>> dataList = bean.getData();


        BigExcelWriter writer = ExcelUtil.getBigWriter(System.currentTimeMillis() + ".xlsx");
        for (List<DataInfo> infos : dataList) {
            int line = writer.getCurrentRow();
            //每个季度
            DataInfo plan = infos.get(0);
            DataInfo summary = infos.get(1);

            List<LinkedHashMap<String, String>> planInfo = plan.getMaps();
            final int column1 = planInfo.get(0).size();

            List<LinkedHashMap<String, String>> summaryInfo = summary.getMaps();
            final int column2 = summaryInfo.get(0).size();

            if (column1 == 0) {
                //没有计划
                continue;
            }else if (column1 == 1) {
                CellUtil.setCellValue(writer.getOrCreateCell(0, line), plan.getTemplateName(), writer.getStyleSet(), true);
            }else {
                writer.merge(line, line, 0, column1 - 1, plan.getTemplateName(), true);
            }

            //写入大总结表头
            if (column2 == 1) {
                CellUtil.setCellValue(writer.getOrCreateCell(column1, line), summary.getTemplateName(), writer.getStyleSet(), true);
            }else if (column2 > 1) {
                writer.merge(line, line, column1, column1 + column2 - 1, summary.getTemplateName(), true);
            }

            writer.setCurrentRow(writer.getCurrentRow() + 1);

            List<Map<String, String>> quarterList = new ArrayList<>();
            Map<String, String> rowMap = new LinkedHashMap<>();
            for (int j = 0, planInfoSize = planInfo.size(); j < planInfoSize; j++) {
                //实体类的map对象的实现要是 LinkedHashMap 才可以保证输出顺序
                rowMap.putAll(planInfo.get(j));
                if (j < summaryInfo.size()) {
                    rowMap.putAll(summaryInfo.get(j));
                }
                quarterList.add(rowMap);
            }
            writer.write(quarterList, true);
        }
        writer.flush().close();
    }


}
