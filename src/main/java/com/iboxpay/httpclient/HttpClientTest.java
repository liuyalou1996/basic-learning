package com.iboxpay.httpclient;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public class HttpClientTest {

  public static void main(String[] args) {
    HttpClient client = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet("http://");
  }

}
