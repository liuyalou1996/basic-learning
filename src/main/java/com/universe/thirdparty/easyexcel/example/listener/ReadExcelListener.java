package com.universe.thirdparty.easyexcel.example.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.universe.thirdparty.easyexcel.example.entity.Staff;
import com.universe.thirdparty.fastjson.FastJsonUtils;

public class ReadExcelListener extends AnalysisEventListener<Staff> {

  private Consumer<List<Staff>> consumer;

  private List<Staff> staffList = new ArrayList<>();

  public ReadExcelListener(Consumer<List<Staff>> consumer) {
    this.consumer = consumer;
  }

  @Override
  public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
    System.out.println("头部信息为：" + FastJsonUtils.toJsonString(headMap));
  }

  @Override
  public void invoke(Staff data, AnalysisContext context) {
    System.out.println("读到了一条数据:" + FastJsonUtils.toJsonString(data));
    staffList.add(data);
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
    System.out.println("数据已全部读取完成!");
    consumer.accept(staffList);
    // 清空list
    staffList.clear();
  }

}
