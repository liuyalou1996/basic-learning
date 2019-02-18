package com.iboxpay.designpattern.creation.service.impl;

import com.iboxpay.designpattern.creation.service.Button;

public class UnixButton implements Button {

  public UnixButton() {
    System.out.println("创建unix操作系统按钮!");
  }
}
