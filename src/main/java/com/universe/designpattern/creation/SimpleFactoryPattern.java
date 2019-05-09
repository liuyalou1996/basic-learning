package com.universe.designpattern.creation;

import com.universe.designpattern.creation.service.Sender;
import com.universe.designpattern.creation.service.impl.MailSender;
import com.universe.designpattern.creation.service.impl.MsgSender;

class SenderFactory {

  public Sender createSender(String type) {
    if ("msg".equals(type)) {
      return new MsgSender();
    } else if ("mail".equals(type)) {
      return new MailSender();
    }

    return null;
  }
}

public class SimpleFactoryPattern {

  public static void main(String[] args) {
    SenderFactory factory = new SenderFactory();
    Sender sender = factory.createSender("msg");
    sender.send();
  }
}
