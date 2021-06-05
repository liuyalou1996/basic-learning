package com.universe.thirdparty.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class EasyExcelUtils {

	/**
	 * 默认从第一个sheet读取
	 */
	private static final int DEFAULT_SHEET_NO = 1;

	/**
	 * 异步读取触发清空list的行数
	 */
	private static final int DEFAULT_BATCH_COUNT = 3000;

	public static <T> List<T> readSheetSynchronously(File file, Class<T> clazz, int headRowNo) throws FileNotFoundException {
		return readSheetSynchronously(file, clazz, headRowNo, DEFAULT_SHEET_NO);
	}

	public static <T> List<T> readSheetSynchronously(File file, Class<T> clazz, int headRowNo, int sheetNo)
		throws FileNotFoundException {
		return readSheetSynchronously(new FileInputStream(file), clazz, headRowNo, sheetNo);
	}

	public static <T> List<T> readSheetSynchronously(InputStream is, Class<T> clazz, int headRowNo) {
		return readSheetSynchronously(is, clazz, headRowNo, DEFAULT_SHEET_NO);
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


	public static <T> void readSheetAsynchronously(File file, Class<T> clazz, Consumer<List<T>> consumer, int headRowNo)
		throws FileNotFoundException {
		readSheetAsynchronously(file, clazz, consumer, DEFAULT_SHEET_NO, headRowNo, DEFAULT_BATCH_COUNT);
	}

	public static <T> void readSheetAsynchronously(File file, Class<T> clazz, Consumer<List<T>> consumer, int sheetNo,
		int headRowNo, int batchCount) throws FileNotFoundException {
		readSheetAsynchronously(new FileInputStream(file), clazz, consumer, sheetNo, headRowNo, batchCount);
	}

	public static <T> void readSheetAsynchronously(InputStream is, Class<T> clazz, Consumer<List<T>> consumer, int headRowNo) {
		readSheetAsynchronously(is, clazz, consumer, DEFAULT_SHEET_NO, headRowNo, DEFAULT_BATCH_COUNT);
	}

	public static <T> void readSheetAsynchronously(InputStream is, Class<T> clazz, Consumer<List<T>> consumer, int sheetNo,
		int headRowNo, int batchCount) {
		if (consumer == null) {
			throw new IllegalArgumentException("The callback interface \"consumer\" cannot be null");
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
	public static <T> void readSheetAsynchronously(InputStream is, Class<T> clazz, ReadListener<T> listener, int sheetNo,
		int headRowNo) {
		validateParams(sheetNo, headRowNo);
		EasyExcel.read(is).registerReadListener(listener).sheet(sheetNo - 1).head(clazz).headRowNumber(headRowNo).autoTrim(true)
			.doRead();
	}


	public static <T> void writeSheet(File dest, List<T> content, boolean needHead) throws FileNotFoundException {
		writeSheet(dest, content, DEFAULT_SHEET_NO, needHead);
	}

	public static <T> void writeSheet(File dest, List<T> content, int sheetNo, boolean needHead) throws FileNotFoundException {
		writeSheet(new FileOutputStream(dest), content, sheetNo, needHead);
	}

	public static <T> void writeSheet(OutputStream dest, List<T> content, boolean needHead) {
		writeSheet(dest, content, DEFAULT_SHEET_NO, needHead);
	}

	/**
	 * 写入到Excel的sheet中
	 * @param dest 输出目的地
	 * @param content 写入内容
	 * @param sheetNo Excel中sheet编号，1代表第一个sheet
	 * @param needHead 是否写入头部信息
	 */
	public static <T> void writeSheet(OutputStream dest, List<T> content, int sheetNo, boolean needHead) {
		validateParams(sheetNo);
		Class<?> clazz = content.get(0).getClass();
		// 写之前父目录一定要存在，不然会报错
		EasyExcel.write
			(dest).head(clazz).sheet(sheetNo - 1).doWrite(content);
	}


	public static <T> void batchWriteSheet(File dest, List<T> content, boolean needHead) throws FileNotFoundException {
		batchWriteSheet(dest, content, DEFAULT_SHEET_NO, DEFAULT_BATCH_COUNT, needHead);
	}

	public static <T> void batchWriteSheet(File dest, List<T> content, int sheetNo, int batchNum, boolean needHead)
		throws FileNotFoundException {
		batchWriteSheet(new FileOutputStream(dest), content, sheetNo, batchNum, needHead);
	}

	public static <T> void batchWriteSheet(OutputStream dest, List<T> content, boolean needHead) {
		batchWriteSheet(dest, content, DEFAULT_SHEET_NO, DEFAULT_BATCH_COUNT, needHead);
	}

	public static <T> void batchWriteSheet(OutputStream dest, List<T> content, int sheetNo, int batchNum, boolean needHead) {
		doBatchWrite(dest, null, content, sheetNo, batchNum, needHead);
	}


	public static <T> void fillSheet(File dest, File template, List<T> content) throws FileNotFoundException {
		fillSheet(dest, template, content, DEFAULT_SHEET_NO);
	}

	public static <T> void fillSheet(File dest, File template, List<T> content, int sheetNo) throws FileNotFoundException {
		fillSheet(new FileOutputStream(dest), new FileInputStream(template), content, sheetNo);
	}

	public static <T> void fillSheet(OutputStream dest, InputStream template, List<T> content) {
		fillSheet(dest, template, content, DEFAULT_SHEET_NO);
	}

	/**
	 * 根据模板填充数据
	 * @param dest 输出目的地
	 * @param template 指定模板
	 * @param content 输出内容
	 * @param sheetNo Excel中sheet编号，1代表第一个sheet
	 */
	public static <T> void fillSheet(OutputStream dest, InputStream template, List<T> content, int sheetNo) {
		validateParams(sheetNo);
		Class<?> clazz = content.get(0).getClass();
		// 填充时不写入头部信息
		EasyExcel.write(dest).head(clazz).withTemplate(template).needHead(false).sheet(sheetNo - 1).doWrite(content);
	}


	public static <T> void batchFillSheet(File dest, File template, List<T> content) throws FileNotFoundException {
		batchFillSheet(new FileOutputStream(dest), new FileInputStream(template), content, DEFAULT_SHEET_NO, DEFAULT_BATCH_COUNT);
	}

	public static <T> void batchFillSheet(File dest, File template, List<T> content, int sheetNo, int batchNum)
		throws FileNotFoundException {
		batchFillSheet(new FileOutputStream(dest), new FileInputStream(template), content, sheetNo, batchNum);
	}

	public static <T> void batchFillSheet(OutputStream dest, InputStream template, List<T> content) {
		batchFillSheet(dest, template, content, DEFAULT_SHEET_NO, DEFAULT_BATCH_COUNT);
	}

	/**
	 * 根据模板批量填充数据，会使用文件缓存(省内存)
	 * @param dest 输出目的地
	 * @param template 指定模板
	 * @param content 输出内容
	 * @param sheetNo Excel中sheet编号，1代表第一个sheet
	 * @param batchNum 分几批处理
	 */
	public static <T> void batchFillSheet(OutputStream dest, InputStream template, List<T> content, int sheetNo, int batchNum) {
		doBatchWrite(dest, template, content, sheetNo, batchNum, false);
	}



	private static <T> void doBatchWrite(OutputStream dest, InputStream template, List<T> content, int sheetNo, int batchNum,
		boolean needHead) {
		if (sheetNo <= 0 || batchNum <= 0) {
			throw new IllegalArgumentException("sheetNo and batchNum must be greater than 0");
		}

		if (CollectionUtils.isEmpty(content)) {
			return;
		}

		ExcelWriterBuilder builder = EasyExcel.write(dest);
		ExcelWriter writer = (template != null) ? builder.withTemplate(template).build() : builder.build();
		WriteSheet sheet = EasyExcel.writerSheet(sheetNo - 1).head(content.get(0).getClass()).needHead(needHead).build();

		int toIndex = 0;
		int size = content.size();
		int batchCount = size / batchNum;
		for (int count = 0; count < batchNum; count++) {
			toIndex = count == (batchNum - 1) ? size : (count + 1) * batchCount;
			writer.write(content.subList(count * batchCount, toIndex), sheet);
		}

		// 清空list，方便垃圾回收
		content.clear();
		// 写完后关闭流
		writer.finish();
	}

	private static void validateParams(int sheetNo) {
		if (sheetNo <= 0) {
			throw new IllegalArgumentException("sheetNo must be greater than 0");
		}
	}

	private static void validateParams(int headRowNo, int sheetNo) {
		if (headRowNo <= 0 || sheetNo <= 0) {
			throw new IllegalArgumentException("headRowNo and sheetNo must be greater than 0");
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

		@SuppressWarnings("deprecation")
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
