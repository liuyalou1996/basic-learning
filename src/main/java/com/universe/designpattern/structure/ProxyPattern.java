package com.universe.designpattern.structure;

interface OrderTicket {

  public void login();

  public void chooseStation();

  public void validate();

  public void pay();
}

class OrderTicketImpl implements OrderTicket {

  private String name;

  public OrderTicketImpl(String name) {
    super();
    this.name = name;
  }

  @Override
  public void login() {
    System.out.println(this.name + "登录12306订票系统!");
  }

  @Override
  public void chooseStation() {
    System.out.println(this.name + "选择乘坐的目的地!");
  }

  @Override
  public void validate() {
    System.out.println(this.name + "通过了身份验证!");
  }

  @Override
  public void pay() {
    System.out.println(this.name + "抢到了火车票并进行付款操作!");
  }

}

class OrderTicketProxy implements OrderTicket {

  private OrderTicket orderTicket;

  public OrderTicketProxy(OrderTicket orderTicket) {
    super();
    this.orderTicket = orderTicket;
  }

  @Override
  public void login() {
    this.orderTicket.login();
  }

  @Override
  public void chooseStation() {
    this.orderTicket.chooseStation();
  }

  @Override
  public void validate() {
    this.orderTicket.validate();
  }

  @Override
  public void pay() {
    this.orderTicket.pay();
  }

}

public class ProxyPattern {

  public static void main(String[] args) {
    OrderTicketProxy proxy = new OrderTicketProxy(new OrderTicketImpl("小明"));
    proxy.login();
    proxy.chooseStation();
    proxy.validate();
    proxy.pay();
  }

}
