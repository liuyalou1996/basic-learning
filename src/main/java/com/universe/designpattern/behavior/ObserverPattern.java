package com.universe.designpattern.behavior;

import java.util.ArrayList;
import java.util.List;

interface Subject {

  public void registerObserver(Observer observer);

  public void deleteObserver(Observer observer);

  public void notifyObserver(String msg);

  default void sendMsg(String msg) {
    notifyObserver(msg);
  }
}

interface Observer {

  public void updateFriendCycle(String msg);
}

class ConcreteSubject implements Subject {

  private List<Observer> observerList = new ArrayList<>();

  @Override
  public void registerObserver(Observer observer) {
    observerList.add(observer);
  }

  @Override
  public void deleteObserver(Observer observer) {
    observerList.remove(observer);
  }

  @Override
  public void notifyObserver(String msg) {
    observerList.forEach(observer -> observer.updateFriendCycle(msg));
  }

}

class ConcreteObserver implements Observer {

  private String name;

  public ConcreteObserver(String name) {
    super();
    this.name = name;
  }

  @Override
  public void updateFriendCycle(String msg) {
    System.out.println(this.name + "收到了一条朋友圈更新：" + msg);
  }

}

/**
 * 观察者模式应用场景：
 * 1、关联行为场景
 * 2、事件多级触发场景
 * 3、跨系统的信息交换场景，如消息队列的处理机制
 * <br/>
 * 涉及角色：
 * 1、抽象主题：该角色又被称为"被观察者"，可以增加和删除观察者对象
 * 2、抽象观察者：该角色为所有的具体观察者定义一个接口，在得到主题的通知时更新自己
 * 3、具体主题：该角色又被称为"具体被观察者"，它将有关状态存入具体观察者对象，在具体主题的内部状态改变时，给所有登记过的观察者发出通知
 * 4、具体观察者角色：该角色实现抽象观察者所要求的更新接口，以便使自身的状态与主题的状态相协调
 */
public class ObserverPattern {

  public static void main(String[] args) {
    ConcreteObserver observer = new ConcreteObserver("小明");
    ConcreteObserver observer2 = new ConcreteObserver("犀利哥");
    ConcreteSubject subject = new ConcreteSubject();
    subject.registerObserver(observer);
    subject.registerObserver(observer2);
    subject.notifyObserver("今天在年会上抽到了一万块钱！");
  }
}
