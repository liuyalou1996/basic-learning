package com.iboxpay.thirdparty.xml.parse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.iboxpay.thirdparty.xml.entity.User;

public class DomParseTest {

  public static void main(String[] args) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    File file = new File(DomParseTest.class.getResource("/").getFile(), "user.xml");

    Document doc = builder.parse(file);
    Element ele = doc.getDocumentElement();
    NodeList nodeList = ele.getElementsByTagName("user");

    List<User> userList = new ArrayList<>();
    for (int count = 0; count < nodeList.getLength(); count++) {
      User user = new User();
      Element sub = (Element) nodeList.item(count);
      int id = Integer.parseInt(sub.getAttribute("id"));
      String name = sub.getElementsByTagName("name").item(0).getFirstChild().getTextContent();
      String password = sub.getElementsByTagName("password").item(0).getFirstChild().getTextContent();
      user.setId(id);
      user.setName(name);
      user.setPassword(password);
      userList.add(user);
    }

    System.out.println(userList);
  }

}
