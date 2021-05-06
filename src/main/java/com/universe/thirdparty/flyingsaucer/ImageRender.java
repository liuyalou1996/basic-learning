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

		// 这种方式CSS样式会不支持
		Java2DRenderer renderer = new Java2DRenderer(source, 1024);
		renderer.setBufferedImageType(BufferedImage.TYPE_INT_ARGB);
		BufferedImage image = renderer.getImage();
		FSImageWriter imageWriter = new FSImageWriter();
		imageWriter.write(image, orginalDest.toString());

		// 支持CSS样式
		BufferedImage img = Graphics2DRenderer.renderToImageAutoSize(source.toURI().toURL().toExternalForm(), 1024, BufferedImage.TYPE_INT_ARGB);
		ImageIO.write(img, "png", g2drDest);
	}
}
