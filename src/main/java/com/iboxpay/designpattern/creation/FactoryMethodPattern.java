package com.iboxpay.designpattern.creation;

import com.iboxpay.designpattern.creation.service.Sender;
import com.iboxpay.designpattern.creation.service.impl.MailSender;
import com.iboxpay.designpattern.creation.service.impl.MsgSender;

interface AbstractSenderFactory {

  Sender createSender();
}

class MsgSenderFactory implements AbstractSenderFactory {

  @Override
  public Sender createSender() {
    return new MsgSender();
  }

}

class MailSenderFactory implements AbstractSenderFactory {

  @Override
  public Sender createSender() {
    return new MailSender();
  }

}

/**
 * 一个抽象工厂对应一个抽象产品
 */
public class FactoryMethodPattern {

  public static void main(String[] args) {
    AbstractSenderFactory msgSenderFac = new MsgSenderFactory();
    AbstractSenderFactory mailSenderFac = new MailSenderFactory();
    Sender msgSender = msgSenderFac.createSender();
    Sender mailSender = mailSenderFac.createSender();
    msgSender.send();
    mailSender.send();
  }
}
