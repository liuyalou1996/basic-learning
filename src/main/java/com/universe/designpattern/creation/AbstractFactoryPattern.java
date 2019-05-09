package com.universe.designpattern.creation;

import com.universe.designpattern.creation.service.Button;
import com.universe.designpattern.creation.service.Text;
import com.universe.designpattern.creation.service.impl.UnixButton;
import com.universe.designpattern.creation.service.impl.UnixText;
import com.universe.designpattern.creation.service.impl.WindowsButton;
import com.universe.designpattern.creation.service.impl.WindowsText;

interface AbstractOSViewFactory {

  Button createButton();

  Text createText();
}

class WindowsOSViewFactory implements AbstractOSViewFactory {

  @Override
  public Button createButton() {
    return new WindowsButton();
  }

  @Override
  public Text createText() {
    return new WindowsText();
  }

}

class UnixOSViewFactory implements AbstractOSViewFactory {

  @Override
  public Button createButton() {
    return new UnixButton();
  }

  @Override
  public Text createText() {
    return new UnixText();
  }

}

/**
 * 一个抽象工厂对应多个抽象产品，抽象工厂模式又名工具箱，其意图是创建一族相关或相互依赖的对象
 */
public class AbstractFactoryPattern {

  public static void main(String[] args) {
    AbstractOSViewFactory winViewFac = new WindowsOSViewFactory();
    AbstractOSViewFactory unixViewFac = new UnixOSViewFactory();
    winViewFac.createButton();
    winViewFac.createText();
    unixViewFac.createButton();
    unixViewFac.createText();
  }
}
