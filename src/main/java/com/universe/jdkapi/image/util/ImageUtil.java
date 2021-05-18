package com.universe.jdkapi.image.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil{
        public static final float DEFAULT_QUALITY = 0.2125f;

	    private ImageUtil() {}

	    /**
	     * 添加图片水印
	     * 
	     * @param targetImg
	     *            目标图片路径，如：C://myPictrue//1.jpg
	     * @param waterImg
	     *            水印图片路径，如：C://myPictrue//logo.png
	     * @param x
	     *            水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
	     * @param y
	     *            水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
	     * @param alpha
	     *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	     */
	    public static void pressImage(String targetImg, String waterImg, int x, int y,
											   float alpha) {
	        File file = new File(targetImg);
			BufferedImage image =pressImage(file, waterImg, x, y, alpha);
			try {
				ImageIO.write((RenderedImage)image,"png",file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// return pressImage(file, waterImg, x, y, alpha);
	    }

	    public static BufferedImage pressImage(File file, String waterImg, int x, int y, float alpha) {
	        try {
	            BufferedImage image = ImageIO.read(file);
	            return pressImage(image, waterImg, x, y, alpha);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public static BufferedImage pressImage(BufferedImage image, String waterImg, int x, int y,
	            float alpha) {
	        try {
	            int width = image.getWidth(null);
	            int height = image.getHeight(null);
	            BufferedImage bufferedImage = new BufferedImage(width, height,
	                    BufferedImage.TYPE_INT_RGB);
	            Graphics2D g = bufferedImage.createGraphics();
	            g.drawImage(image, 0, 0, width, height, null);
	            // 水印文件
	            Image waterImage = ImageIO.read(new File(waterImg)); 
	            int waterWidth = waterImage.getWidth(null);
	            int waterHeight = waterImage.getHeight(null);
	            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
	            int widthDiff = width - waterWidth;
	            int heightDiff = height - waterHeight;
	            if (x < 0) {
	                x = widthDiff / 2;
	            } else if (x > widthDiff) {
	                x = widthDiff;
	            }
	            if (y < 0) {
	                y = heightDiff / 2;
	            } else if (y > heightDiff) {
	                y = heightDiff;
	            }
	            // 水印文件结束
	            g.drawImage(waterImage, x, y, waterWidth, waterHeight, null); 
	            g.dispose();
	            return bufferedImage;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    /**
	     * 添加文字水印
	     * 
	     * @param targetImg
	     *            目标图片路径，如：C://myPictrue//1.jpg
	     * @param pressText
	     *            水印文字， 如：中国证券网
	     * @param fontName
	     *            字体名称， 如：宋体
	     * @param fontStyle
	     *            字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
	     * @param fontSize
	     *            字体大小，单位为像素
	     * @param color
	     *            字体颜色
	     * @param x
	     *            水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
	     * @param y
	     *            水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
	     * @param alpha
	     *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	     */
	    public static BufferedImage pressText(String targetImg, String pressText, String fontName,
	            int fontStyle, int fontSize, Color color, int x, int y, float alpha) {
	        File file = new File(targetImg);
	        return pressText(file, pressText, fontName, fontStyle, fontSize, color, x, y, alpha);
	    }

	    public static BufferedImage pressText(File file, String pressText, String fontName,
	            int fontStyle, int fontSize, Color color, int x, int y, float alpha) {
	        try {
	            BufferedImage image = ImageIO.read(file);
	            return pressText(image, pressText, fontName, fontStyle, fontSize, color, x, y, alpha);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;

	    }
	    
	    public static BufferedImage pressText(BufferedImage image, String pressText, String fontName,
	            int fontStyle, int fontSize, String color, int x, int y, float alpha) {
	        Color cc = new Color(Integer.parseInt(color, 16));
	        return pressText(image, pressText, fontName, fontStyle, fontSize, cc, x, y, alpha);
	    }

	    public static BufferedImage pressText(BufferedImage image, String pressText, String fontName,
	            int fontStyle, int fontSize, Color color, int x, int y, float alpha) {
	        try {
	            int width = image.getWidth(null);
	            int height = image.getHeight(null);
	            BufferedImage bufferedImage = new BufferedImage(width, height,
	                    BufferedImage.TYPE_INT_RGB);
	            Graphics2D g = bufferedImage.createGraphics();
	            g.drawImage(image, 0, 0, width, height, null);
	            g.setFont(new Font(fontName, fontStyle, fontSize));
	            g.setColor(color);
	            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
	            int textWidth = fontSize * getLength(pressText);
	            int textHeight = fontSize;
	            int widthDiff = width - textWidth;
	            int heightDiff = height - textHeight;
	            if (x < 0) {
	                x = widthDiff / 2;
	            } else if (x > widthDiff) {
	                x = widthDiff;
	            }
	            if (y < 0) {
	                y = heightDiff / 2;
	            } else if (y > heightDiff) {
	                y = heightDiff;
	            }

	            g.drawString(pressText, x, y + textHeight);
	            g.dispose();
	            return bufferedImage;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    /**
	     * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
	     * 
	     * @param text
	     * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
	     */
	    public static int getLength(String text) {
	        int textLength = text.length();
	        int length = textLength;
	        for (int i = 0; i < textLength; i++) {
	            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
	                length++;
	            }
	        }
	        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
	    }
}