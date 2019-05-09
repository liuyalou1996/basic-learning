package com.universe.designpattern.creation.service.impl;

import com.universe.designpattern.creation.service.Button;

public class UnixButton implements Button {

  public UnixButton() {
    System.out.println("创建unix操作系统按钮!");
  }
}
