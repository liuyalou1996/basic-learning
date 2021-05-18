package com.universe.jdkapi.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author 刘亚楼
 * @date 2021/5/18
 */
public class ZoomImageTest {

	private static final String BASE_PATH = System.getProperty("user.home") + File.separator + "image";

	private static final int WIDTH = 500;

	private static final int HEIGHT = 300;

	public static void main(String[] args) throws IOException {
		BufferedImage destImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = destImage.createGraphics();
		// 读取原始位图
		BufferedImage sourceImage = ImageIO.read(new File(BASE_PATH, "source.jpg"));
		// 将原始位图缩小后绘制到
		graphics2D.drawImage(sourceImage, 0, 0, WIDTH, HEIGHT, null);
		graphics2D.dispose();

		// 输出到磁盘
		ImageIO.write(destImage, "jpg", new File(BASE_PATH, "dest.jpg"));
	}

}
