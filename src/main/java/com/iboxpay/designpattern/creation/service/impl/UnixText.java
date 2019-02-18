package com.iboxpay.designpattern.creation.service.impl;

import com.iboxpay.designpattern.creation.service.Text;

public class UnixText implements Text {

  public UnixText() {
    System.out.println("创建unix操作系统文本框!");
  }
}
