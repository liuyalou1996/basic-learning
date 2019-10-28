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

  /**
   * 同步读取Excel中的第一个sheet，头部信息占一行，即从第二行开始读取数据
   * @param file 
   * @param clazz 使用运行时类型为clazz的对象保存数据
   * @return
   * @throws FileNotFoundException
   */
  public static <T> List<T> readSheetSynchronously(File file, Class<T> clazz) throws FileNotFoundException {
    return readSheetSynchronously(file, clazz, DEFAULT_SHEET_NO, DEFAULT_HEADROW_NO);
  }

  /**
   * 同步读取Excel中的第一个sheet，第二行开始读取数据(头部信息占一行)
   * @param is 输入流
   * @param clazz 使用运行时类型为clazz的对象实例保存数据
   * @return
   */
  public static <T> List<T> readSheetSynchronously(InputStream is, Class<T> clazz) {
    return readSheetSynchronously(is, clazz, DEFAULT_SHEET_NO, DEFAULT_HEADROW_NO);
  }

  /**
   * 同步读取Excel中的sheet
   * @param file 
   * @param clazz 使用运行时类型为clazz的对象保存数据
   * @param headRowNo 头部信息所占行数
   * @param sheetNo Excel中sheet编号，1代表第一个sheet
   * @return
   * @throws FileNotFoundException
   */
  public static <T> List<T> readSheetSynchronously(File file, Class<T> clazz, int headRowNo, int sheetNo) throws FileNotFoundException {
    return readSheetSynchronously(new FileInputStream(file), clazz, headRowNo, sheetNo);
  }

  /**
   * 同步读取Excel中的sheet 
   * @param is
   * @param clazz 使用运行时类型为clazz的对象保存数据
   * @param headRowNo 头部信息所占行数
   * @param sheetNo Excel中sheet编号，1代表第一个sheet
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T> List<T> readSheetSynchronously(InputStream is, Class<T> clazz, int headRowNo, int sheetNo) {
    validateParams(headRowNo, sheetNo);
    // 文件输入流会自动关闭
    return (List<T>) EasyExcel.read(is).sheet(sheetNo - 1).head(clazz).headRowNumber(headRowNo).autoTrim(true).doReadSync();
  }

  /**
   * 异步读取Excel中的sheet，默认读取第一个sheet，从第二行读取(头部信息为一行)
   * @param file 
   * @param clazz 使用运行时类型为clazz的对象保存数据
   * @param consumer 回调接口，默认每读batchCount行或者全部读取完毕会回调该接口
   * @throws FileNotFoundException
   */
  public static <T> void readSheetAsynchronously(File file, Class<T> clazz, Consumer<List<T>> consumer) throws FileNotFoundException {
    readSheetAsynchronously(file, clazz, consumer, DEFAULT_SHEET_NO, DEFAULT_HEADROW_NO, DEFAULT_BATCH_COUNT);
  }

  public static <T> void readSheetAsynchronously(File file, Class<T> clazz, Consumer<List<T>> consumer, int batchCount)
      throws FileNotFoundException {
    readSheetAsynchronously(file, clazz, consumer, DEFAULT_SHEET_NO, DEFAULT_HEADROW_NO, batchCount);
  }

  public static <T> void readSheetAsynchronously(File file, Class<T> clazz, Consumer<List<T>> consumer, int sheetNo, int headRowNo,
      int batchCount) throws FileNotFoundException {
    readSheetAsynchronously(new FileInputStream(file), clazz, consumer, sheetNo, headRowNo, batchCount);
  }

  public static <T> void readSheetAsynchronously(InputStream is, Class<T> clazz, Consumer<List<T>> consumer) {
    readSheetAsynchronously(is, clazz, consumer, DEFAULT_SHEET_NO, DEFAULT_HEADROW_NO, DEFAULT_BATCH_COUNT);
  }

  public static <T> void readSheetAsynchronously(InputStream is, Class<T> clazz, Consumer<List<T>> consumer, int batchCount) {
    readSheetAsynchronously(is, clazz, consumer, DEFAULT_SHEET_NO, DEFAULT_HEADROW_NO, batchCount);
  }

  /**
   * 异步读取Excel中的sheet
   * @param file 所在文件
   * @param clazz 使用运行时类型为clazz的对象保存数据
   * @param consumer 回调接口，每读batchCount行或者全部读取完毕会回调该接口
   * @param sheetNo Excel中sheet编号，1代表第一个sheet
   * @param headRowNo Excel中头部信息所占行数，为1则从第二行读取数据
   * @param batchCount 每次读取最多保存到list的对象数量
   */
  public static <T> void readSheetAsynchronously(InputStream is, Class<T> clazz, Consumer<List<T>> consumer, int sheetNo, int headRowNo,
      int batchCount) {
    if (consumer == null) {
      throw new RuntimeException("The callback interface consumer cannot be null");
    }

    readSheetAsynchronously(is, clazz, new DefaultReadListener<T>(consumer, batchCount), sheetNo, headRowNo);
  }

  /**
   * 异步读取Excel中的sheet
   * @param is 输入流
   * @param clazz 使用运行时类型为clazz的对象保存数据
   * @param listener 回调监听器
   * @param sheetNo Excel中sheet编号，1代表第一个sheet
   * @param headRowNo Excel中头部信息所占行数，为1则从第二行读取数据
   */
  public static <T> void readSheetAsynchronously(InputStream is, Class<T> clazz, ReadListener<T> listener, int sheetNo, int headRowNo) {
    validateParams(sheetNo, headRowNo);
    EasyExcel.read(is).registerReadListener(listener).sheet(sheetNo - 1).head(clazz).headRowNumber(headRowNo).autoTrim(true).doRead();
  }

  private static void validateParams(int headRowNo, int sheetNo) {
    if (headRowNo <= 0 || sheetNo <= 0) {
      throw new RuntimeException("Invalid params,headRowNo and sheetNo must be greater than 0");
    }
  }

  /**
   * Excel异步读取监听器，每读batchCount行会清空list中的所有数据，防止过多对象占用内存
   * @author liuyalou
   * @date 2019年10月27日
   */
  private static class DefaultReadListener<T> extends AnalysisEventListener<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultReadListener.class);

    private int batchCount;

    private List<T> result;

    private Consumer<List<T>> consumer;

    public DefaultReadListener(Consumer<List<T>> consumer, int batchCount) {
      this.consumer = consumer;
      this.batchCount = batchCount;
      this.result = new ArrayList<>();
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
      result.add(data);
      if (result.size() >= batchCount) {
        invokeCallback();
      }

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
      LOGGER.info("Begin to read sheet,current sheet's name is: {}", context.readSheetHolder().getSheetName());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
      LOGGER.info("Sheet has been all read,total count is: {}", context.readSheetHolder().getTotal());
      // 读取完所有行之后再次回调传输剩余的数据
      if (!result.isEmpty()) {
        invokeCallback();
      }
    }

    @Override
    public void onException(Exception e, AnalysisContext context) throws Exception {
      int rowNo = context.readRowHolder().getRowIndex();
      LOGGER.error("Exception occurs when reading sheet,current row number is:{}", (rowNo + 1), e);
    }

    private void invokeCallback() {
      consumer.accept(result);
      result.clear();
    }

  }

}
