package com.universe.thirdparty.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘亚楼
 * @date 2022/9/22
 */
public abstract class QrCodeUtils {

	/**
	 * 默认生成的文件
	 */
	private static final String DEFAULT_FORMAT = "png";

	/**
	 * 默认额外参数：可设置字符集、容错级别
	 */
	private static final Map<EncodeHintType, Object> DEFAULT_HINTS = new HashMap<>();

	static {
		DEFAULT_HINTS.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
		DEFAULT_HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
	}

	/**
	 * 生成绿码
	 * @param content 二维码展示内容
	 * @param width 二维码图片宽度
	 * @param height 二维码图片高度
	 * @return 图片二进制流
	 * @throws Exception
	 */
	public static byte[] generateGreenQrCode(String content, int width, int height) throws Exception {
		return generateQrCode(content, width, height, Color.GREEN.getRGB(), Color.WHITE.getRGB());
	}

	/**
	 * 生成二维码并转为二进制流
	 * @param content 二维码展示内容
	 * @param width 二维码图片宽度
	 * @param height 二维码图片高度
	 * @param onColorAsRGB 二维码数据图案颜色
	 * @param offColorAsRGB 二维码背景色
	 * @return 图片二进制流
	 * @throws Exception
	 */
	public static byte[] generateQrCode(String content, int width, int height, int onColorAsRGB, int offColorAsRGB) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		generateQrCode(baos, content, width, height, onColorAsRGB, offColorAsRGB);
		return baos.toByteArray();
	}

	/**
	 * 生成二维码
	 * @param os 输出流
	 * @param content 二维码展示内容
	 * @param width 二维码图片宽度
	 * @param height 二维码图片高度
	 * @param onColorAsRGB 二维码数据图案颜色
	 * @param offColorAsRGB 二维码背景色
	 * @throws Exception
	 */
	public static void generateQrCode(OutputStream os, String content, int width, int height, int onColorAsRGB, int offColorAsRGB) throws Exception {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, DEFAULT_HINTS);
		MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(onColorAsRGB, offColorAsRGB);
		MatrixToImageWriter.writeToStream(bitMatrix, DEFAULT_FORMAT, os, matrixToImageConfig);
	}

	/**
	 * 生成二维码
	 * @param path 文件路径
	 * @param content 二维码展示内容
	 * @param width 二维码图片宽度
	 * @param height 二维码图片高度
	 * @param onColorAsRGB 二维码数据图案颜色
	 * @param offColorAsRGB 二维码背景色
	 * @throws Exception
	 */
	public static void generateQrCode(String path, String content, int width, int height, int onColorAsRGB, int offColorAsRGB) throws Exception {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, DEFAULT_HINTS);
		MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(onColorAsRGB, offColorAsRGB);
		MatrixToImageWriter.writeToPath(bitMatrix, DEFAULT_FORMAT, Paths.get(path), matrixToImageConfig);
	}

	/**
	 * 重置图片大小
	 * @param rawImage 原图片
	 * @param targetWidth 目标图片宽度
	 * @param targetHeight 目标图片高度
	 * @return
	 */
	public static BufferedImage resizeImage(BufferedImage rawImage, int targetWidth, int targetHeight) {
		Image scaledImage = rawImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_REPLICATE);
		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		resizedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
		return resizedImage;
	}

	// public static void main(String[] args) throws Exception {
	// 	String path = "C:\\Users\\mc\\Desktop\\qrcode.png";
	// 	String content = "https://lingbomanbu.com/ah-counter/report/overview/769256790536441856";
	// 	int width = 300;
	// 	int height = 300;
	// 	generateQrCode(path, content, width, height, Color.GREEN.getRGB(), Color.WHITE.getRGB());
	// }




	public static void main(String[] args) throws Exception {
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

		String content = "https://www.baidu.com";
		String qrCodePath = "C:\\Users\\mc\\Desktop\\qrcode.png";
		String logoPath = "C:\\Users\\mc\\Desktop\\logo_mini.png";

		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);

		// 写入输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, DEFAULT_FORMAT, baos);

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		BufferedImage qrCodeImage = ImageIO.read(bais);
		Graphics2D graphics2D = qrCodeImage.createGraphics();

		BufferedImage logoImage = ImageIO.read(Paths.get(logoPath).toFile());
		// 这里将logo的位置居中
		int logoX = (qrCodeImage.getWidth() - logoImage.getWidth()) / 2;
		int logoY = (qrCodeImage.getHeight() - logoImage.getHeight()) / 2;
		// 在二维码画布上绘图
		graphics2D.drawImage(logoImage, null, logoX, logoY);
		graphics2D.dispose();

		// 输出绘制后logo的二维码图片
		ImageIO.write(qrCodeImage, DEFAULT_FORMAT, Paths.get(qrCodePath).toFile());
	}

}
