package com.universe.jvm;

/**
 * VM Args:-Xss128k
 */
public class JavaVMStackSOF {

  private int stackLength = 1;

  public void stackLeak() {
    this.stackLength++;
    stackLeak();
  }

  public int getStackLength() {
    return stackLength;
  }

  public void setStackLength(int stackLength) {
    this.stackLength = stackLength;
  }

  public static void main(String[] args) {
    JavaVMStackSOF oom = new JavaVMStackSOF();
    try {
      oom.stackLeak();
    } catch (Throwable e) {
      System.out.println("stack length:" + oom.getStackLength());
      throw e;
    }
  }

}
