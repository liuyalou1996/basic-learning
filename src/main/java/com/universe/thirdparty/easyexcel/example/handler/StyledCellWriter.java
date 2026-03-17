package com.universe.thirdparty.easyexcel.example.handler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

/**
 * @author Nick Liu
 * @date 2026/3/17
 */
@Slf4j
@AllArgsConstructor
public class StyledCellWriter implements CellWriteHandler {

	private String headerName;

	@Override
	public int order() {
		return Integer.MIN_VALUE;
	}

	@Override
	public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head,
		Integer relativeRowIndex, Boolean isHead) {
		if (isHead) {
			return;
		}

		int headerColumnIndex = getHeaderIndex(writeSheetHolder);
		System.out.println("当前头部索引: %d".formatted(headerColumnIndex));
		if (headerColumnIndex <= -1) {
			return;
		}
		if (cell.getColumnIndex() != headerColumnIndex) {
			return;
		}

		System.out.println("当前列值: %s".formatted(cell.getStringCellValue()));
		Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
		Font font = workbook.createFont();
		font.setColor(IndexedColors.GREEN.getIndex());
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
	}

	private int getHeaderIndex(WriteSheetHolder writeSheetHolder) {
		Map<Integer, Head> map = writeSheetHolder.getExcelWriteHeadProperty().getHeadMap();
		for (Map.Entry<Integer, Head> entry : map.entrySet()) {
			Head head = entry.getValue();
			if (headerName.equals(head.getHeadNameList().get(0))) {
				return entry.getKey();
			}
		}
		return -1;
	}

}
