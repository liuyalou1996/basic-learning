package com.universe.jdkapi.image;

import javax.imageio.ImageIO;
import java.util.Arrays;

/**
 * @author 刘亚楼
 * @date 2021/5/18
 */
public class ImageIOTest {

	public static void main(String[] args) {
		// 列出ImageIO所有能读的图形文件非正式格式名称
		String[] supportedReaderFormatNames = ImageIO.getReaderFormatNames();
		// 列出ImageIO所有能读的图像文件后缀
		String[] supportedReaderFileSuffixes = ImageIO.getReaderFileSuffixes();
		// 列出ImageIO所有能写的图形文件非正式格式名称
		String[] supportedWriterFormatNames = ImageIO.getWriterFormatNames();
		// 列出ImageIO所有能写的图形文件后缀
		String[] supportedWriterFileSuffixes = ImageIO.getWriterFileSuffixes();

		System.out.println("ImageIO能读的所有图形文件格式");
		System.out.println(Arrays.toString(supportedReaderFormatNames));
		System.out.println(Arrays.toString(supportedReaderFileSuffixes));

		System.out.println("ImageIO能写的所有图形文件格式");
		System.out.println(Arrays.toString(supportedWriterFormatNames));
		System.out.println(Arrays.toString(supportedWriterFileSuffixes));
	}
}
