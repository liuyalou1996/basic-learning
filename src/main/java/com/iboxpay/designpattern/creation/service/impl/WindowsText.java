package com.iboxpay.designpattern.creation.service.impl;

import com.iboxpay.designpattern.creation.service.Text;

public class WindowsText implements Text {

  public WindowsText() {
    System.out.println("创建windows操作系统文本框!");
  }
}
