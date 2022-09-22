package com.universe.thirdparty.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.io.FileUtils;

import javax.activation.MimeType;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
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

	private static final Map<EncodeHintType, Object> DEFAULT_HINTS = new HashMap<>();

	static {
		DEFAULT_HINTS.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
		DEFAULT_HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
	}

	public static byte[] generateQrCode(String content, int width, int height, int onColorAsRGB, int offColorAsRGB) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		generateQrCode(baos, content, width, height, onColorAsRGB, offColorAsRGB);
		return baos.toByteArray();
	}

	public static void generateQrCode(OutputStream os, String content, int width, int height, int onColorAsRGB, int offColorAsRGB) throws Exception {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, DEFAULT_HINTS);
		MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(onColorAsRGB, offColorAsRGB);
		MatrixToImageWriter.writeToStream(bitMatrix, DEFAULT_FORMAT, os, matrixToImageConfig);
	}

	public static void generateQrCode(String path, String content, int width, int height, int onColorAsRGB, int offColorAsRGB) throws Exception {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, DEFAULT_HINTS);
		MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(onColorAsRGB, offColorAsRGB);
		MatrixToImageWriter.writeToPath(bitMatrix, DEFAULT_FORMAT, Paths.get(path), matrixToImageConfig);
	}

	public static void main(String[] args) throws Exception {
		String path = "C:\\Users\\mc\\Desktop\\qrcode.png";
		String content = "https://lingbomanbu.com/ah-counter/report/overview/769256790536441856";
		int width = 300;
		int height = 300;
		generateQrCode(path, content, width, height, Color.GREEN.getRGB(), Color.WHITE.getRGB());
	}

}
