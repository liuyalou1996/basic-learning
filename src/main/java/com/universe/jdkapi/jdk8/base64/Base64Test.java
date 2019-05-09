package com.universe.jdkapi.jdk8.base64;

import java.nio.charset.Charset;
import java.util.Base64;

public class Base64Test {

  public static void main(String[] args) {
    byte[] bytes = "runoob?java8".getBytes(Charset.forName("UTF-8"));

    String basicEncodedStr = Base64.getEncoder().encodeToString(bytes);
    String rawStr = new String(Base64.getDecoder().decode(basicEncodedStr), Charset.forName("UTF-8"));

    String urlEncodedStr = Base64.getUrlEncoder().encodeToString(bytes);
    String mimeEncodedStr = Base64.getMimeEncoder().encodeToString(bytes);
    System.out.println("原始字符串为：" + rawStr);
    System.out.println("Basic Base64编码后的字符串为：" + basicEncodedStr);
    System.out.println("URL Base64编码后的字符串为：" + urlEncodedStr);
    System.out.println("Mime Base64编码后的字符串为：" + mimeEncodedStr);
  }

}
