package com.universe.thirdparty.flyingsaucer;

import com.itextpdf.text.pdf.BaseFont;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author 刘亚楼
 * @date 2021/5/6
 */
public class PDFRender {

	public static void createPdfWithChinese() throws Exception {
		String basePath = System.getProperty("user.home") + File.separator + "flyingsaucer";
		String source = Paths.get(basePath, "test.html").toString();
		File dest = Paths.get(basePath, "saucer.pdf").toFile();

		try (OutputStream os = new FileOutputStream(dest)) {
			ITextRenderer renderer = new ITextRenderer();
			ITextFontResolver fontResolver = renderer.getFontResolver();
			// 必须添加能支持中文的字体，否则html内容有中文会不显示，同时body标签要设置font-family: SimSun
			String fontPath = PDFRender.class.getResource("/font/simsun.ttc").getPath();
			fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

			renderer.setDocument(new File(source));
			renderer.layout();
			renderer.createPDF(os);
			renderer.finishPDF();
		}
	}

	public static void createPdfWithHeaderAndFooter() throws Exception {
		String basePath = Paths.get(System.getProperty("user.home"), "pdf").toString();
		String source = PDFRender.class.getResource("/template/personalAccountProof.html").getPath();
		Path dest = Paths.get(basePath, "personalAccountProof.pdf");
		try (OutputStream os = Files.newOutputStream(dest)) {
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(new File(source));
			renderer.layout();
			renderer.createPDF(os);
			renderer.finishPDF();
		}
	}

	public static void main(String[] args) throws Exception {
		createPdfWithHeaderAndFooter();
	}
}
