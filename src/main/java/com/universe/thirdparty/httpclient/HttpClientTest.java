package com.universe.thirdparty.httpclient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.universe.thirdparty.fastjson.JsonUtils;

public class HttpClientTest {

  private static HttpClient httpClient = HttpClients.createDefault();

  /**
   * 发送get请求
   * @throws Exception 
   */
  public static void sendGet() throws Exception {
    HttpGet httpGet = new HttpGet("http://localhost:8080/wechat_public_platform/getMerchant.json");
    // 注意关闭资源，HttpResponse接口本身没有继承Autocloseable接口，而CloseableHttpResponse继承了Closeable接口
    try (CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpGet)) {
      int statusCode = response.getStatusLine().getStatusCode();
      // 状态码在[200,300)之间代表请求已经被接收、理解和接受
      if (statusCode >= HttpStatus.SC_OK && statusCode < HttpStatus.SC_MULTIPLE_CHOICES) {
        System.out.println(EntityUtils.toString(response.getEntity(), Consts.UTF_8));
      }
    } catch (Exception e) {
      throw e;
    }
  }

  public static void sendPostInHtmlForm() throws IOException {
    HttpPost httpPost = new HttpPost("http://localhost:8080/wechat_public_platform/getMerchant2.json");
    List<NameValuePair> formParams = new ArrayList<>();
    formParams.add(new BasicNameValuePair("mid", "1"));
    formParams.add(new BasicNameValuePair("mname", "liuyalou"));
    formParams.add(new BasicNameValuePair("mno", "123456"));
    HttpEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
    httpPost.setEntity(entity);
    try (CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpPost)) {
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode >= HttpStatus.SC_OK && statusCode < HttpStatus.SC_MULTIPLE_CHOICES) {
        System.out.println(EntityUtils.toString(response.getEntity()));
      }
    } catch (IOException e) {
      throw e;
    }
  }

  public static void sendPostInJsonFormat() throws IOException {
    HttpPost httpPost = new HttpPost("http://localhost:8080/wechat_public_platform/getMerchant3.json");
    Map<String, Object> params = new HashMap<>();
    params.put("mid", "2");
    params.put("mname", "liuqian");
    params.put("mno", "1234567");
    StringEntity entity = new StringEntity(JsonUtils.toJsonString(params), ContentType.APPLICATION_JSON);
    httpPost.setEntity(entity);
    try (CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpPost)) {
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode >= HttpStatus.SC_OK && statusCode < HttpStatus.SC_MULTIPLE_CHOICES) {
        System.out.println(EntityUtils.toString(response.getEntity()));
      }
    } catch (IOException e) {
      throw e;
    }
  }

  public static void uploadFile() throws IOException {
    HttpPost httpPost = new HttpPost("http://localhost:8080/wechat_public_platform/upload.json");
    byte[] content = FileUtils.readFileToByteArray(new File("C:\\Users\\Public\\Pictures\\Pictures\\壁纸\\壁纸2.jpg"));
    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    // 注意：上传文件时文件名和表单域名都要传
    builder.addBinaryBody("portrait", content, ContentType.APPLICATION_OCTET_STREAM, "image");
    HttpEntity entity = builder.build();
    httpPost.setEntity(entity);
    try (CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpPost)) {
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode >= HttpStatus.SC_OK && statusCode < HttpStatus.SC_MULTIPLE_CHOICES) {
        System.out.println(EntityUtils.toString(response.getEntity()));
      }

    } catch (IOException e) {
      throw e;
    }
  }

  public static void main(String[] args) throws Exception {
    sendGet();
  }

}
