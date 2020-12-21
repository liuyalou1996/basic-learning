package com.universe.thirdparty.easyexcel.example;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.universe.thirdparty.easyexcel.example.entity.BankBranch;
import com.universe.thirdparty.easyexcel.example.entity.Staff;
import com.universe.thirdparty.easyexcel.example.listener.ReadExcelListener;
import com.universe.thirdparty.easyexcel.example.listener.ReadMergedCellExcelListener;
import com.universe.thirdparty.fastjson.JsonUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

public class ReadExcelExample {

	private static final String BASE_PATH = System.getProperty("user.home") + File.separator + "test";

	public static void readSingleExcel() {
		File file = new File(BASE_PATH, "staff.xlsx");
		Consumer<List<Staff>> consumer = staffList -> System.out.println("读到的数据为：" + staffList);
		// 默认读取第一个sheet，表头为第一行
		EasyExcel.read(file, Staff.class, new ReadExcelListener(consumer)).sheet(0).doRead();
	}

	public static void readMultipleSameExcel() {
		File file = new File(BASE_PATH, "staff.xlsx");
		Consumer<List<Staff>> consumer = staffList -> System.out.println("读到的数据为：" + staffList);
		// 默认读取第一个sheet
		ExcelReader reader = EasyExcel.read(file).autoTrim(true).build();
		ReadSheet firstSheet = EasyExcel.readSheet(0).head(Staff.class).registerReadListener(new ReadExcelListener(consumer)).build();
		ReadSheet secondSheet = EasyExcel.readSheet(1).head(Staff.class).registerReadListener(new ReadExcelListener(consumer)).build();
		reader.read(firstSheet);
		reader.read(secondSheet);
		reader.finish();
	}

	public static void readExtraInfoFromExcel() {
		InputStream is = ReadExcelExample.class.getResourceAsStream("/excel/BNC Website Content.xlsx");
		ReadMergedCellExcelListener<BankBranch> listener = new ReadMergedCellExcelListener<>();
		EasyExcel.read(is, BankBranch.class, listener)
			.extraRead(CellExtraTypeEnum.MERGE)
			.sheet(0)
			.headRowNumber(2)
			.autoTrim(true)
			.doRead();
		List<BankBranch> resultList = listener.getResultList();
		List<CellExtra> cellExtraList = listener.getCellExtraList();
		cellExtraList.forEach(cellExtra -> {
			for (int index = 0; index < resultList.size(); index++) {
				if (index > cellExtra.getFirstRowIndex() - 2 && index <= cellExtra.getLastRowIndex() - 2) {
					// 减去头行
					resultList.get(index).setServiceEn(resultList.get(cellExtra.getRowIndex() - 2).getServiceEn());
					resultList.get(index).setServiceIn(resultList.get(cellExtra.getRowIndex() - 2).getServiceIn());
				}
			}
		});
		System.out.println(JsonUtils.toPrettyJsonStringWithNullValue(resultList));
	}

	public static void main(String[] args) {
		readExtraInfoFromExcel();
	}

}
