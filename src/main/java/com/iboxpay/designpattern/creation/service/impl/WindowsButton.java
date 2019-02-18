package com.iboxpay.designpattern.creation.service.impl;

import com.iboxpay.designpattern.creation.service.Button;

public class WindowsButton implements Button {

  public WindowsButton() {
    System.out.println("创建windows操作系统按钮!");
  }
}
