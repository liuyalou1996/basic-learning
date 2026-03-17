package com.universe.thirdparty.easyexcel.example.handler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Map;

/**
 * @author Nick Liu
 * @date 2026/3/18
 */
@AllArgsConstructor
public class StyledRowHandler implements RowWriteHandler {

	private int headRowIndex = -1;

	private String headerName;

	public StyledRowHandler(String headerName) {
		this.headerName = headerName;
	}

	// @Override
	// public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
	// 	int headerColumnIndex = getHeaderIndex(writeSheetHolder);
	// 	if (headerColumnIndex <= -1) {
	// 		return;
	// 	}
	//
	// 	Cell cell = row.getCell(headerColumnIndex);
	// 	Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
	// 	Font font = workbook.createFont();
	// 	font.setColor(IndexedColors.GREEN.getIndex());
	// 	CellStyle cellStyle = workbook.createCellStyle();
	// 	cellStyle.setFont(font);
	// 	cell.setCellStyle(cellStyle);
	// }

	@Override
	public void afterRowDispose(RowWriteHandlerContext context) {
		WriteSheetHolder writeSheetHolder = context.getWriteSheetHolder();
		int headerColumnIndex = getHeaderIndex(writeSheetHolder);
		if (headerColumnIndex <= -1) {
			return;
		}

		Cell cell = context.getRow().getCell(headerColumnIndex);
		Workbook workbook = context.getWriteWorkbookHolder().getWorkbook();
		Font font = workbook.createFont();
		font.setColor(IndexedColors.GREEN.getIndex());
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
	}

	private int getHeaderIndex(WriteSheetHolder writeSheetHolder) {
		if (this.headRowIndex >= 0) {
			return this.headRowIndex;
		}

		Map<Integer, Head> map = writeSheetHolder.getExcelWriteHeadProperty().getHeadMap();
		for (Map.Entry<Integer, Head> entry : map.entrySet()) {
			Head head = entry.getValue();
			if (headerName.equals(head.getHeadNameList().get(0))) {
				this.headRowIndex = entry.getKey();
				return headRowIndex;
			}
		}

		return -1;
	}
}
