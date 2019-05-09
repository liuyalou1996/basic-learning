package com.universe.jdkapi.nio2;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileAttributeViewTest {

  private static final DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

  public static void main(String[] args) throws Exception {
    File file = new File(FileAttributeViewTest.class.getResource("").getPath());
    Path path = Paths.get(file.getAbsolutePath(), "AttributeViewTest.class");

    // 获取文件基本信息
    BasicFileAttributeView basicView = Files.getFileAttributeView(path, BasicFileAttributeView.class);
    BasicFileAttributes basicAttrs = basicView.readAttributes();

    Date creationTime = new Date(basicAttrs.creationTime().toMillis());
    Date lastAccessTime = new Date(basicAttrs.lastAccessTime().toMillis());
    Date lastModifiedTime = new Date(basicAttrs.lastModifiedTime().toMillis());
    long size = basicAttrs.size() / 1024;
    System.out.println("文件创建时间为：" + df.format(creationTime));
    System.out.println("文件最后访问时间为：" + df.format(lastAccessTime));
    System.out.println("文件最后修改时间为：" + df.format(lastModifiedTime));
    System.out.println("文件大小为：" + size + "kb");

    // 获取文件属主信息
    FileOwnerAttributeView ownerView = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
    UserPrincipal principal = ownerView.getOwner();
    System.out.println("文件的拥有者为：" + principal);

    UserPrincipal prin = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByName("liuyalou");
    System.out.println(prin.getName());

    path = Paths.get(System.getProperty("user.home"), "hidden.txt");
    // 获取访问dos属性的视图
    DosFileAttributeView dosView = Files.getFileAttributeView(path, DosFileAttributeView.class);
    // 设为隐藏
    dosView.setReadOnly(true);
    // 设为只读
    dosView.setHidden(true);
  }

}
