package com.iboxpay.jdkapi.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Cglib代理实际上是为目标类创建子类，并重写目标类的方法，由于是继承，因此不能对final修饰的类进行代理
 */
public class CglibProxyMethodInterceptor implements MethodInterceptor {

  private Object targetObject;

  public CglibProxyMethodInterceptor(Object targetObject) {
    this.targetObject = targetObject;
  }

  public Object createProxyInstance() {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(targetObject.getClass());
    enhancer.setCallback(this);
    return enhancer.create();
  }

  /**
   * 第一个参数为被增强的对象，也就是目标对象，第二个参数为被拦截的方法，第三个参数为方法参数，第四个参数为方法代理，用来调用父类对象的方法
   */
  @Override
  public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
    System.err.println("=================目标对象方法执行之前进行拦截======================");
    // 调用目标对象的方法，也可以使用method.invoke(obj,args),但没通过MethodProxy调用快
    Object value = proxy.invokeSuper(obj, args);
    System.err.println("=================目标对象方法执行之后进行拦截======================");
    return value;
  }

}
