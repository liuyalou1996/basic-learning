package com.universe.thirdparty.easyexcel.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.universe.thirdparty.easyexcel.Staff;
import com.universe.thirdparty.fastjson.JsonUtils;

public class ReadDataListener extends AnalysisEventListener<Staff> {

  /**
   * 每次读5条
   */
  private static final int BATCH_COUNT = 5;

  private Consumer<List<Staff>> consumer;

  private List<Staff> staffList = new ArrayList<>();

  public ReadDataListener(Consumer<List<Staff>> consumer) {
    this.consumer = consumer;
  }

  @Override
  public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
    System.out.println("头部信息为：" + JsonUtils.toJsonString(headMap));
  }

  @Override
  public void invoke(Staff data, AnalysisContext context) {
    System.out.println("读到了一条数据:" + JsonUtils.toJsonString(data));
    staffList.add(data);

    if (BATCH_COUNT <= staffList.size()) {
      return;
    }

  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
    System.out.println("数据已全部读取完成!");
    consumer.accept(staffList);
    // 清空list
    staffList.clear();
  }

}
