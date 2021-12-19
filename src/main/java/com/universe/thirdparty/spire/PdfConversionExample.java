package com.universe.thirdparty.spire;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author 刘亚楼
 * @date 2021/12/19
 */
public class PdfConversionExample {

	public static void main(String[] args) {
		String basePath = System.getProperty("user.home") + File.separator + "test";
		Path sourcePath = Paths.get(basePath, "阿里开发手册嵩山版.pdf");
		Path destPath = Paths.get(basePath, "阿里开发手册嵩山版.html");
		System.out.println(sourcePath.toAbsolutePath());

		PdfDocument pdfDocument = new PdfDocument();
		pdfDocument.loadFromFile(sourcePath.toFile().getAbsolutePath());
		pdfDocument.saveToFile(destPath.toAbsolutePath().toString(), FileFormat.HTML);
	}
}
