package com.universe.designpattern.behavior;

abstract class StrategyOfPlan {

  public abstract void useStrategy();

}

class StrategyA extends StrategyOfPlan {

  @Override
  public void useStrategy() {
    System.out.println("执行A计划!");
  }

}

class StrategyB extends StrategyOfPlan {

  @Override
  public void useStrategy() {
    System.out.println("执行B计划!");
  }

}

class StrategyC extends StrategyOfPlan {

  @Override
  public void useStrategy() {
    System.out.println("执行C计划!");
  }

}

class PlanContext {

  private StrategyOfPlan sop;

  public PlanContext(StrategyOfPlan sop) {
    super();
    this.sop = sop;
  }

  public void chooseStrategy() {
    sop.useStrategy();
  }

}

/**
 * 策略模式应用场景：
 * 1、多个类只是在算法或行为上稍有不同的场景
 * 2、算法需要自由切换的场景
 * 3、需要屏蔽算法规则的场景
 * <br/>
 * 涉及角色：
 * 1、上下文(Context)：屏蔽高层模块对策略、算法的直接访问，它持有一个对Strategy的引用
 * 2、抽象策略(Abstract Strategy)：该角色对策略、算法进行抽象，通常定义每个策略或算法必须具有的方法和属性
 * 3、具体策略(Concrete Strategy)：该角色实现了抽象策略中的具体操作，含有具体的算法
 */
public class StrategyPattern {

  public static void main(String[] args) {
    PlanContext context = new PlanContext(new StrategyA());
    context.chooseStrategy();
  }
}
