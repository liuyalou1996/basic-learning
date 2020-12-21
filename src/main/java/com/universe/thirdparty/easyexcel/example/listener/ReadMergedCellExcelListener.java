package com.universe.thirdparty.easyexcel.example.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.universe.thirdparty.fastjson.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 刘亚楼
 * @date 2020/12/21
 */
public class ReadMergedCellExcelListener<T> extends AnalysisEventListener<T> {

	private List<T> resultList = new ArrayList<>();

	private List<CellExtra> cellExtraList = new ArrayList<>();

	@Override
	public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
		System.out.println("头部信息为：" + JsonUtils.toJsonString(headMap));
	}

	@Override
	public void invoke(T data, AnalysisContext context) {
		System.out.println("读到了一条数据:" + JsonUtils.toJsonString(data));
		resultList.add(data);
	}

	@Override
	public void extra(CellExtra extra, AnalysisContext context) {
		System.out.println("读取到了一条额外信息" + JsonUtils.toJsonString(extra));
		if (CellExtraTypeEnum.MERGE == extra.getType()) {
			System.out.println("额外信息是合并单元格，内容为：" + extra.getText());
		}
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		System.out.println("数据已全部读取完成!");
	}

	public List<T> getResultList() {
		return resultList;
	}

	public List<CellExtra> getCellExtraList() {
		return cellExtraList;
	}
}
