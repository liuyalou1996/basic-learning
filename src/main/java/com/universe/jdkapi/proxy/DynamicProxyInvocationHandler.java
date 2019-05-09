package com.universe.jdkapi.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理为面向接口的代理，被代理的类必须实现接口
 */
public class DynamicProxyInvocationHandler implements InvocationHandler {

  private Object targetObject;

  public DynamicProxyInvocationHandler(Object targetObject) {
    this.targetObject = targetObject;
  }

  public Object createProxyInstance() {
    return Proxy.newProxyInstance(DynamicProxyInvocationHandler.class.getClassLoader(),
        targetObject.getClass().getInterfaces(), this);
  }

  /**
   * 第一个参数为代理对象，第二个参数为被代理的方法，第三个参数为方法参数
   */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.err.println("=================目标对象方法执行之前进行拦截======================");
    Object value = method.invoke(targetObject, args);
    System.err.println("=================目标对象方法执行之后进行拦截======================");
    return value;
  }
}
