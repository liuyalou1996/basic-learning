package com.iboxpay.designpattern.creation.service.impl;

import com.iboxpay.designpattern.creation.service.Sender;

public class MailSender implements Sender {

  @Override
  public void send() {
    System.out.println("发送邮件！");
  }

}
