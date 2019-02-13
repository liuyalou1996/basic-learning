package com.iboxpay.xml.parse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.iboxpay.xml.entity.User;

public class Dom4jParseTest {

  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws Exception {
    File file = new File(Dom4jParseTest.class.getResource("/").getFile(), "user.xml");
    SAXReader reader = new SAXReader();
    Document doc = reader.read(file);

    Element root = doc.getRootElement();
    List<Element> eleList = root.elements("user");
    List<User> userList = new ArrayList<>();

    eleList.forEach(ele -> {
      int id = Integer.parseInt(ele.attribute("id").getValue());
      String name = ele.elementText("name");
      String password = ele.elementText("password");
      User user = new User();
      user.setId(id);
      user.setName(name);
      user.setPassword(password);
      userList.add(user);
    });

    System.out.println(userList);
  }

}
