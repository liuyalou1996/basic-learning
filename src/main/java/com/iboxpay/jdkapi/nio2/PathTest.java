package com.iboxpay.jdkapi.nio2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathTest {

  public static void main(String[] args) throws Exception {
    // Path代表与平台无关的路径
    Path path = Paths.get("");
    Path absolutePath = path.toAbsolutePath();
    System.out.println("相对路径：" + path.toString());
    System.out.println("绝对路径：" + absolutePath.toString());
    System.out.println("父路径：" + absolutePath.getParent());
    System.out.println("根路径：" + absolutePath.getRoot());
    // 路径中元素的数量，如：E:\workspace8\basic-learning中，一个斜杠代表一个元素
    System.out.println("路径元素数量：" + absolutePath.getNameCount());

    Path path2 = Paths.get("F:", "Oracle", "product");
    System.out.println("构造的绝对路径：" + path2);

    String basePath = System.getProperty("user.home");
    Path link = Paths.get(basePath, "ref.txt");

    // 创建软链接，相当于快捷方式
    Path softLink = Files.createSymbolicLink(link, Paths.get(basePath, "link.txt"));
    System.out.println(Files.isSymbolicLink(softLink));
    System.out.println(Files.size(softLink));
  }

}
