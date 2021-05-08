package com.universe.thirdparty.flyingsaucer;

import org.xhtmlrenderer.simple.Graphics2DRenderer;
import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xhtmlrenderer.util.FSImageWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author 刘亚楼
 * @date 2021/5/6
 */
public class ImageRender {

	public static void main(String[] args) throws IOException {
		String basePath = System.getProperty("user.home") + File.separator + "flyingsaucer";
		File source = new File(basePath, "test.html");
		File orginalDest = new File(basePath, "original.png");
		File g2drDest = new File(basePath, "G2DR.png");

		// 有些css样式不支持，如背景颜色
		Java2DRenderer renderer = new Java2DRenderer(source, 1024);
		renderer.setBufferedImageType(BufferedImage.TYPE_INT_RGB);
		BufferedImage java2dImage = renderer.getImage();
		new FSImageWriter().write(java2dImage, orginalDest.toString());

		System.out.println(source.toURI().toURL().toExternalForm());

		// 支持css样式，如背景颜色
		BufferedImage g2drImage = Graphics2DRenderer.renderToImageAutoSize(source.toURI().toURL().toExternalForm(), 1024, BufferedImage.TYPE_INT_ARGB);
		ImageIO.write(g2drImage, "png", g2drDest);
	}
}
