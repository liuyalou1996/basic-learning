package com.universe.jdkapi.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 给图片加水印工具类
 * @author 刘亚楼
 * @date 2021/5/18
 */
public class WatermarkExample {

	private static final String BASE_PATH = System.getProperty("user.home") + File.separator + "image";

	public static void main(String[] args) throws IOException {
		addWatermarkWithImage();
	}

	private static void addWatermarkWithImage() throws IOException {
		BufferedImage sourceImage = ImageIO.read(new File(BASE_PATH, "source.jpg"));
		int sourceWidth = sourceImage.getWidth();
		int sourceHeight = sourceImage.getHeight();

		BufferedImage destImage = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_BGR);
		Graphics2D graphics2D = destImage.createGraphics();
		Image scaledImage = sourceImage.getScaledInstance(sourceWidth, sourceHeight, Image.SCALE_SMOOTH);
		// 比例按原图来，先填图片
		graphics2D.drawImage(scaledImage, 0, 0, null);

		BufferedImage watermarkImage = ImageIO.read(new File(BASE_PATH, "watermarkIcon.jpg"));
		// 透明度范围在[0.0,1.0]
		graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

		int watermarkWidth = sourceWidth / 5;
		int watermarkHeight = sourceHeight / 5;
		graphics2D.drawImage(watermarkImage, 100, 200, watermarkWidth, watermarkHeight, null);
		graphics2D.dispose();

		// 写入磁盘
		ImageIO.write(destImage, "jpg", new File(BASE_PATH, "watermarkWithIcon.jpg"));
	}

	private static void addWatermarkWithText() throws IOException {
		BufferedImage sourceImage = ImageIO.read(new File(BASE_PATH, "source.jpg"));
		int sourceWidth = sourceImage.getWidth();
		int sourceHeight = sourceImage.getHeight();

		BufferedImage destImage = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_BGR);
		Graphics2D graphics2D = destImage.createGraphics();

		// 比例按原图来，先填图片
		graphics2D.drawImage(sourceImage, 0, 0, sourceWidth, sourceHeight, null);

		// 填充文字
		String watermarkText = "水印内容";
		// 支持的Font参考GraphicsEnvironment#getAllFonts，实现类为Win32GraphicsEnvironment
		Font watermarkFont = new Font("宋体", Font.PLAIN, 30);
		graphics2D.setFont(watermarkFont);
		graphics2D.setColor(Color.RED);

		FontMetrics fontMetrics = graphics2D.getFontMetrics(watermarkFont);
		int watermarkLength = fontMetrics.charsWidth(watermarkText.toCharArray(), 0, watermarkText.length());
		int watermarkX = sourceWidth - watermarkLength - 5;
		int watermarY = sourceHeight - fontMetrics.getLeading() - 5;
		graphics2D.drawString(watermarkText, watermarkX, watermarY);
		graphics2D.dispose();

		// 写入磁盘
		ImageIO.write(destImage, "jpg", new File(BASE_PATH, "watermarkWithText.jpg"));
	}
}
