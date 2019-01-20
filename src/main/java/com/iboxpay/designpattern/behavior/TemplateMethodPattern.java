package com.iboxpay.designpattern.behavior;

abstract class AbstractAccount {

  private String accountNo;

  public AbstractAccount(String accountNo) {
    super();
    this.accountNo = accountNo;
  }

  protected abstract String getAccountType();

  protected abstract double getInterestRate();

  private double calculateAmount(String accountNo, String accountType) {
    // 模拟数据库获取相关信息
    return 1000;
  }

  public double calculateInteterest() {
    String accountType = this.getAccountType();
    double interestRate = this.getInterestRate();
    double amount = calculateAmount(this.accountNo, accountType);
    return amount * interestRate;
  }

}

class DemandAccount extends AbstractAccount {

  public DemandAccount(String accountNo) {
    super(accountNo);
  }

  @Override
  protected String getAccountType() {
    return "活期账户";
  }

  @Override
  protected double getInterestRate() {
    return 0.0038;
  }

}

class FixedAccount extends AbstractAccount {

  public FixedAccount(String accountNo) {
    super(accountNo);
  }

  @Override
  protected String getAccountType() {
    return "定期账户";
  }

  @Override
  protected double getInterestRate() {
    return 0.0478;
  }

}

/**
 * 模板方法模式应用场景：
 * 1、多个子类有公共方法，并且逻辑基本相同时
 * 2、可以把重要的、复杂的核心算法设计为模板方法，周边相关的细节功能则由各个子类实现
 * 3、重构时，模板方法模式是一种经常使用的模式，将相同的代码抽取出来放到父类中
 */
public class TemplateMethodPattern {

  public static void main(String[] args) {
    AbstractAccount demandAccount = new DemandAccount("00000");
    AbstractAccount fixedAccount = new FixedAccount("11111");
    System.out.println("活期账户利息为：" + demandAccount.calculateInteterest());
    System.out.println("定期账户利息为：" + fixedAccount.calculateInteterest());
  }

}
