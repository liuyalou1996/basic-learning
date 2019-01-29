package com.iboxpay.jdkapi.nio2;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FilesTest {

  public static void main(String[] args) throws Exception {
    String basePath = System.getProperty("user.home");
    Path path = Paths.get(basePath, "readme.txt");
    System.out.println("是否是隐藏文件：" + Files.isHidden(path));

    // 默认使用UTF-8编码
    List<String> content = Files.readAllLines(path, StandardCharsets.UTF_8);
    content.forEach(System.out::println);
    System.out.println("文件大小为：" + Files.size(path));

    Path dest = Paths.get(basePath, "poem.txt");
    // 写入文件，默认使用UTF-8
    Files.write(dest, Arrays.asList("海阔凭鱼跃", "天高任鸟飞"));

    System.out.println("当前目录下文件和文件夹为：");
    // 列出当前目录
    Files.list(Paths.get("")).forEach(System.out::println);

    System.out.println("使用jdk8 api读取文件：");
    Files.lines(path).forEach(System.out::println);

    FileStore store = Files.getFileStore(Paths.get("C:"));
    System.out.println("C盘总容量为：" + store.getTotalSpace() / 1024 / 1024 / 1024 + "GB");
    System.out.println("C盘可用空间为：" + store.getUsableSpace() / 1024 / 1024 / 1024 + "GB");
  }

}
