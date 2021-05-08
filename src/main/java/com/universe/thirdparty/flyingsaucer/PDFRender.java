package com.universe.thirdparty.flyingsaucer;

import com.itextpdf.text.pdf.BaseFont;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

/**
 * @author 刘亚楼
 * @date 2021/5/6
 */
public class PDFRender {

	public static void main(String[] args) throws Exception {
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
		}
	}
}
