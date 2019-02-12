package com.iboxpay.jdkapi.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**<p>
 * Channel类似于传统的流对象，但与传统的的流对象有两个主要区别:<br/>
 * </p>
 * 1、Channel与传统的InputStream和OutputStream最大的区别在于它提供了一个map()方法，通过该方法可以直接将"一块数据"映射到内存中。
 * 简单来说就是直接把文件部分或全部直接映射成buffer,如果说传统的输入/输出系统是面向流的处理，则新IO则是面向块的处理。<br/>
 * 2、程序不能直接访问Channel中的数据，包括读取、写入都不行，Channel只能与buffer进行交互。<br/>
 * 
 */
public class FileChannelTest {

  private static final String BASE_PATH = System.getProperty("user.home") + File.separator;

  private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

  private static final CharsetDecoder CHAETSET_DECODER = DEFAULT_CHARSET.newDecoder();

  public static void copyFileContent() {
    File file = new File(BASE_PATH + "readme.txt");

    try (FileChannel inchannel = new FileInputStream(file).getChannel();
        FileChannel outChannel = new FileOutputStream(BASE_PATH + "copied.txt").getChannel()) {
      // 将Channel对应的文件的一块区域直接映射到内存中，映射到内存后不要再调用flip方法
      MappedByteBuffer byteBuffer = inchannel.map(MapMode.READ_ONLY, 0, file.length());
      CharBuffer charBuffer = CHAETSET_DECODER.decode(byteBuffer);
      System.out.println(charBuffer.toString());

      // 写入数据，这里不需要调用clear方法
      outChannel.write(byteBuffer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void appendContentToFile() {
    File file = new File(BASE_PATH + "appended.txt");
    try (FileChannel channel = new RandomAccessFile(file, "rw").getChannel()) {
      MappedByteBuffer buffer = channel.map(MapMode.READ_ONLY, 0, file.length());
      // 将位置
      channel.position(file.length());
      channel.write(buffer);
      System.out.println("追加成功！");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 如果担心Channel对应的文件过大，使用map()方法一次将所有的文件内容映射到内存中引起性能下降，可使用传统"竹筒多次取水"的方式
   */
  public static void readFileTraditionally() {
    File file = new File(BASE_PATH + "readme.txt");
    try (FileChannel channel = new FileInputStream(file).getChannel()) {
      ByteBuffer buffer = ByteBuffer.allocate(1024);
      while (channel.read(buffer) != -1) {
        // 为从buffer中读取数据做准备
        buffer.flip();
        CharBuffer charBuffer = CHAETSET_DECODER.decode(buffer);
        System.out.println(charBuffer.toString());

        // 为向buffer中装入数据做准备
        buffer.clear();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {

  }

}
