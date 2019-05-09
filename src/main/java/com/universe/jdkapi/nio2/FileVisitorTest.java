package com.universe.jdkapi.nio2;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileVisitorTest {

  public static void main(String[] args) throws Exception {
    SimpleFileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {

      @Override
      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("正在访问目录：" + dir.toAbsolutePath());
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println("正在访问文件：" + file.toAbsolutePath());
        if (file.endsWith("FileVisitorTest.java")) {
          return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.CONTINUE;
      }

    };

    Path path = Paths.get("");
    Files.walkFileTree(path, visitor);
  }

}
