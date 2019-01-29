package com.iboxpay.jdkapi.nio2;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class WatchServiceTest {

  public static void main(String[] args) throws Exception {
    // 获取文件系统的WatchService对象
    WatchService watcher = FileSystems.getDefault().newWatchService();
    String dir = WatchServiceTest.class.getResource("").getFile();
    Path path = Paths.get(new File(dir).getAbsolutePath());
    System.out.println("监控的文件路径：" + path.toAbsolutePath());
    // 注册监视器
    path.register(watcher, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);

    while (true) {
      // 获取下一个文件变化事件
      WatchKey key = watcher.take();
      for (WatchEvent<?> event : key.pollEvents()) {
        // 上下文返回的是相对路径
        System.out.println(event.context() + "文件发生了" + event.kind() + "事件!");
      }

      boolean isValid = key.reset();
      if (!isValid) {
        break;
      }
    }
  }
}
