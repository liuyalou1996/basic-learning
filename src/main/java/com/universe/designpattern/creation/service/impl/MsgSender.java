package com.universe.designpattern.creation.service.impl;

import com.universe.designpattern.creation.service.Sender;

public class MsgSender implements Sender {

  @Override
  public void send() {
    System.out.println("发送短信！");
  }

}
