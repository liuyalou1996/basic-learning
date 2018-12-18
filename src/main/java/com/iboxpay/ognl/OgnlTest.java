package com.iboxpay.ognl;

import java.util.HashMap;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

/**
 * <p><b>OGNL有三大要素，分别是表达式、根对象和Context对象</b></p>
 * <p>表达式是整个OGNL的核心，OGNL会根据表达式去对象中取值，所有OGNL操作都是针对表达式解析后进行的，它表明了此次OGNL操作要做什么。</p>
 * <p>Root对象可以理解为OGNL的操作对象，规定了对谁进行操作。</p>
 * <p>上下文环境规定了OGNL的操作在哪进行，上下文环境是一个Map类型的对象，在表达式中访问Context的对象需要"#"号加上对象名称。</p>
 * @author liuyalou
 * @since 2018年12月15日
 * <p>
 */
public class OgnlTest {

  public static void compare() throws OgnlException {
    OgnlContext context = new OgnlContext();
    // OGNL比较机制与javascript中的比较机制一样，不同类型进行比较，先转换为boolean型，再作比较。数字0和空字符都为false
    boolean flag = (boolean) Ognl.getValue("''==0", context, context.getRoot());
    System.out.println(flag);
  }

  public static void invokeStaticMethodAndVariable() throws Exception {
    OgnlContext context = new OgnlContext();
    boolean isBlank =
        (boolean) Ognl.getValue("@org.apache.commons.lang3.StringUtils@isBlank('')", context, context.getRoot());
    int maxValue = (int) Ognl.getValue("@java.lang.Integer@MAX_VALUE", context, context.getRoot());
    System.out.println(isBlank);
    System.out.println(maxValue);
  }

  public static void invokeInstatnceMethod() throws Exception {
    Map<String, Object> infoMap = new HashMap<>();
    infoMap.put("name", "lyl");
    infoMap.put("age", "22");
    OgnlContext context = new OgnlContext();
    context.put("student", infoMap);
    // 此句也可写为Ognl.getValue("#student.get('name')", context, context.getRoot());
    String value = (String) Ognl.getValue("#student.name", context, context.getRoot());
    System.out.println("调用实例方法的值为：" + value);
  }

  public static void invokeInstanceMethodFromRoot() throws OgnlException {
    Map<String, Object> infoMap = new HashMap<>();
    infoMap.put("name", "lyl");
    infoMap.put("age", "22");
    OgnlContext context = new OgnlContext();
    context.setRoot(infoMap);
    String value = (String) Ognl.getValue("name", context, context.getRoot());
    System.out.println("从Root中获取的值为：" + value);
  }

  public static void main(String[] args) throws Exception {
    compare();
    invokeStaticMethodAndVariable();
    invokeInstatnceMethod();
    invokeInstanceMethodFromRoot();
  }

}
