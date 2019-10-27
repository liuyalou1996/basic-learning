package com.universe.thirdparty.easyexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;

public abstract class EasyExcelUtils {

  /**
   * 默认从第一个sheet读取
   */
  private static final int DEFAULT_SHEET_NO = 1;

  /**
   * 默认头部信息占一行，即从第二行开始读取
   */
  private static final int DEFAULT_HEADROW_NO = 1;

  /**
   * 异步读取触发清空list的行数
   */
  private static final int DEFAULT_BATCH_COUNT = 3000;

  public static <T> List<T> readSingleSheetSynchronously(File file, Class<T> clazz) throws FileNotFoundException {
    return readSingleSheetSynchronously(file, clazz, DEFAULT_SHEET_NO, DEFAULT_HEADROW_NO);
  }

  public static <T> List<T> readSingleSheetSynchronously(InputStream is, Class<T> clazz) {
    return readSingleSheetSynchronously(is, clazz, DEFAULT_SHEET_NO, DEFAULT_HEADROW_NO);
  }

  public static <T> List<T> readSingleSheetSynchronously(File file, Class<T> clazz, int headRowNo, int sheetNo)
      throws FileNotFoundException {
    return readSingleSheetSynchronously(new FileInputStream(file), clazz, headRowNo, sheetNo);
  }

  @SuppressWarnings("unchecked")
  public static <T> List<T> readSingleSheetSynchronously(InputStream is, Class<T> clazz, int headRowNo, int sheetNo) {
    validateParams(headRowNo, sheetNo);
    // 文件输入流会自动关闭
    return (List<T>) EasyExcel.read(is).sheet(sheetNo - 1).head(clazz).headRowNumber(headRowNo).autoTrim(true)
        .doReadSync();
  }

  public static <T> void readSingleSheetAsynchronously(File file, Class<T> clazz, Consumer<List<T>> consumer)
      throws FileNotFoundException {
    readSingleSheetAsynchronously(file, clazz, consumer, DEFAULT_SHEET_NO, DEFAULT_HEADROW_NO, DEFAULT_BATCH_COUNT);
  }

  public static <T> void readSingleSheetAsynchronously(File file, Class<T> clazz, Consumer<List<T>> consumer,
      int batchCount) throws FileNotFoundException {
    readSingleSheetAsynchronously(file, clazz, consumer, DEFAULT_SHEET_NO, DEFAULT_HEADROW_NO, batchCount);
  }

  public static <T> void readSingleSheetAsynchronously(File file, Class<T> clazz, Consumer<List<T>> consumer,
      int sheetNo, int headRowNo, int batchCount) throws FileNotFoundException {
    readSingleSheetAsynchronously(new FileInputStream(file), clazz, consumer, sheetNo, headRowNo, batchCount);
  }

  public static <T> void readSingleSheetAsynchronously(InputStream is, Class<T> clazz, Consumer<List<T>> consumer) {
    readSingleSheetAsynchronously(is, clazz, consumer, DEFAULT_SHEET_NO, DEFAULT_HEADROW_NO, DEFAULT_BATCH_COUNT);
  }

  public static <T> void readSingleSheetAsynchronously(InputStream is, Class<T> clazz, Consumer<List<T>> consumer,
      int batchCount) {
    readSingleSheetAsynchronously(is, clazz, consumer, DEFAULT_SHEET_NO, DEFAULT_HEADROW_NO, batchCount);
  }

  /**
   * 异步读取Excel中的sheet
   * @param file 所在文件
   * @param clazz 解析后保存的运行时类型
   * @param consumer 回调接口，每读batchCount行会回调该接口
   * @param sheetNo Excel中sheet编号，1代表第一个sheet
   * @param headRowNo Excel中头部信息所占行数，为1则从第二行读取数据
   * @param batchCount list中保存的最大行数
   */
  public static <T> void readSingleSheetAsynchronously(InputStream is, Class<T> clazz, Consumer<List<T>> consumer,
      int sheetNo, int headRowNo, int batchCount) {
    readSingleSheetAsynchronously(is, clazz, new BatchReadListener<T>(consumer, batchCount), sheetNo, headRowNo);
  }

  /**
   * 异步读取Excel中的sheet
   * @param is 输入流
   * @param clazz 解析后保存的运行时类型
   * @param listener 回调监听器
   * @param sheetNo Excel中sheet编号，1代表第一个sheet
   * @param headRowNo Excel中头部信息所占行数，为1则从第二行读取数据
   */
  public static <T> void readSingleSheetAsynchronously(InputStream is, Class<T> clazz, ReadListener<T> listener,
      int sheetNo, int headRowNo) {
    validateParams(sheetNo, headRowNo);
    EasyExcel.read(is).registerReadListener(listener).sheet(sheetNo - 1).head(clazz).headRowNumber(headRowNo)
        .autoTrim(true).doRead();
  }

  private static void validateParams(int headRowNo, int sheetNo) {
    if (headRowNo <= 0 || sheetNo <= 0) {
      throw new RuntimeException("Invalid params,headRowNo and sheetNo must be greater than 0");
    }
  }

  /**
   * Excel异步读取监听器，每读batchCount行清空list中的所有数据，方便垃圾回收
   * @author liuyalou
   * @date 2019年10月27日
   */
  private static class BatchReadListener<T> extends AnalysisEventListener<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchReadListener.class);

    private int batchCount;

    private List<T> list;

    private Consumer<List<T>> consumer;

    public BatchReadListener(Consumer<List<T>> consumer, int batchCount) {
      this.consumer = consumer;
      this.batchCount = batchCount;
      this.list = new ArrayList<>();
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
      list.add(data);
      if (list.size() >= batchCount) {
        consumer.accept(list);
        list.clear();
      }

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
      LOGGER.info("Begin to read sheet,sheet's name is: {}", context.readSheetHolder().getSheetName());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
      LOGGER.info("Sheet has been all read,total count is: {}", context.readSheetHolder().getTotal());
      // 读取完所有行之后再次回调传输剩余的数据
      if (!list.isEmpty()) {
        consumer.accept(list);
      }
    }

    @Override
    public void onException(Exception e, AnalysisContext context) throws Exception {
      int rowNo = context.readRowHolder().getRowIndex();
      LOGGER.error("Exception occurs when reading sheet,current row number is:{}", (rowNo + 1), e.getMessage(), e);
    }

  }

}
