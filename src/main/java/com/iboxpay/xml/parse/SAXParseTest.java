package com.iboxpay.xml.parse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.iboxpay.xml.entity.User;

public class SAXParseTest {

  private static class XmlParseHandler extends DefaultHandler {

    private List<User> userList;

    private User user;

    private String currentTag;

    @Override
    public void startDocument() throws SAXException {
      System.out.println("============开始解析xml文档============");
      userList = new ArrayList<>();
    }

    @Override
    public void endDocument() throws SAXException {
      System.out.println("============xml文档解析结束============");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if ("user".equals(qName)) {
        user = new User();
        int id = Integer.parseInt(attributes.getValue("id"));
        user.setId(id);
      }

      currentTag = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
      if ("user".equals(qName)) {
        userList.add(user);
      }

      currentTag = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
      String value = new String(ch, start, length);
      if ("name".equals(currentTag)) {
        user.setName(value);
      } else if ("password".equals(currentTag)) {
        user.setPassword(value);
      }
    }

    public List<User> getUserList() {
      return userList;
    }

  }

  public static void main(String[] args) throws Exception {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = factory.newSAXParser();
    File file = new File(SAXParseTest.class.getResource("/").getFile(), "user.xml");
    XmlParseHandler handler = new XmlParseHandler();
    parser.parse(file, handler);
    System.out.println(handler.getUserList());
  }

}
