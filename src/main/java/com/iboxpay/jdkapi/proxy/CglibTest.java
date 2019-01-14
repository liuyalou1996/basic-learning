package com.iboxpay.jdkapi.proxy;

class StudentBizImpl {

  public void addStudent() {
    System.err.println("添加了一个学生。。。。");
  }
}

public class CglibTest {

  public static void main(String[] args) {
    StudentBizImpl impl = new StudentBizImpl();
    CglibProxyMethodInterceptor interceptor = new CglibProxyMethodInterceptor(impl);
    StudentBizImpl proxyInstance = (StudentBizImpl) interceptor.createProxyInstance();
    proxyInstance.addStudent();
  }

}
