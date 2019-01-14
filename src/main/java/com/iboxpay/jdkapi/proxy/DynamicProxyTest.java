package com.iboxpay.jdkapi.proxy;

interface Testable {

  public void test();
}

class TestableImpl implements Testable {

  @Override
  public void test() {
    System.err.println("this is the instance implementing Tesable.");
  }

}

public class DynamicProxyTest {

  public static void main(String[] args) {
    Testable testable = new TestableImpl();
    DynamicProxyInvocationHandler handler = new DynamicProxyInvocationHandler(testable);
    Testable proxyInstance = (Testable) handler.createProxyInstance();
    proxyInstance.test();
  }

}
