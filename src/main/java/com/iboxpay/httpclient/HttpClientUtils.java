package com.iboxpay.httpclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.iboxpay.fastjson.CollectionUtils;

public class HttpClientUtils {

  private static HttpClient httpClient = HttpClients.createDefault();

  public static HttpClientResp sendGet(String url, Map<String, Object> headers, Map<String, Object> params)
      throws Exception {
    URIBuilder uriBuilder = new URIBuilder(url);
    if (CollectionUtils.isNotEmpty(params)) {
      for (Map.Entry<String, Object> param : params.entrySet()) {
        uriBuilder.setParameter(param.getKey(), String.valueOf(param.getValue()));
      }
    }

    HttpGet httpGet = new HttpGet(uriBuilder.build());
    setHeaders(httpGet, headers);
    return getResponse(httpGet);
  }

  public static HttpClientResp sendPostInHtmlForm(String url, Map<String, Object> headers, Map<String, Object> params)
      throws IOException {
    HttpPost httpPost = new HttpPost(url);
    setHeaders(httpPost, headers);
    if (CollectionUtils.isNotEmpty(params)) {
      List<NameValuePair> formParams = new ArrayList<>();
      for (Map.Entry<String, Object> param : params.entrySet()) {
        formParams.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
      }
      httpPost.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));
    }

    return getResponse(httpPost);
  }

  public static HttpClientResp sendPostInJsonFormat(String url, Map<String, Object> headers, String jsonStr)
      throws IOException {
    HttpPost httpPost = new HttpPost(url);
    setHeaders(httpPost, headers);
    httpPost.setEntity(new StringEntity(jsonStr, ContentType.APPLICATION_JSON));
    return getResponse(httpPost);
  }

  public static void setHeaders(AbstractHttpMessage message, Map<String, Object> headers) {
    if (CollectionUtils.isNotEmpty(headers)) {
      for (Map.Entry<String, Object> header : headers.entrySet()) {
        message.setHeader(header.getKey(), String.valueOf(header.getValue()));
      }
    }
  }

  private static HttpClientResp getResponse(HttpRequestBase request) throws IOException {
    try (CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(request)) {
      HttpClientResp resp = new HttpClientResp();
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode >= HttpStatus.SC_OK && statusCode < HttpStatus.SC_MULTIPLE_CHOICES) {
        Map<String, String> headers = new HashMap<>();
        for (Header header : response.getAllHeaders()) {
          headers.put(header.getName(), header.getValue());
        }

        HttpEntity httpEntity = response.getEntity();
        resp.setSuccessful(true);
        resp.setHeaders(headers);
        resp.setContentType(httpEntity.getContentType().getValue());
        resp.setContentLength(httpEntity.getContentLength());
        resp.setRespStr(EntityUtils.toString(httpEntity, Consts.UTF_8));
        if (httpEntity.getContentEncoding() != null) {
          resp.setContentEncoding(httpEntity.getContentEncoding().getValue());
        }
      }

      return resp;
    } catch (IOException e) {
      throw e;
    }

  }

  public static class HttpClientResp {

    private String respStr;
    private long contentLength;
    private String contentType;
    private String contentEncoding;
    private Map<String, String> headers;
    private boolean successful;

    public String getRespStr() {
      return respStr;
    }

    public long getContentLength() {
      return contentLength;
    }

    public String getContentType() {
      return contentType;
    }

    public String getContentEncoding() {
      return contentEncoding;
    }

    public Map<String, String> getHeaders() {
      return headers;
    }

    public boolean isSuccessful() {
      return successful;
    }

    public void setRespStr(String respStr) {
      this.respStr = respStr;
    }

    public void setContentLength(long contentLength) {
      this.contentLength = contentLength;
    }

    public void setContentType(String contentType) {
      this.contentType = contentType;
    }

    public void setContentEncoding(String contentEncoding) {
      this.contentEncoding = contentEncoding;
    }

    public void setHeaders(Map<String, String> headers) {
      this.headers = headers;
    }

    public void setSuccessful(boolean successful) {
      this.successful = successful;
    }

    @Override
    public String toString() {
      return "HttpClientResp [respStr=" + respStr + ", contentLength=" + contentLength + ", contentType=" + contentType
          + ", contentEncoding=" + contentEncoding + ", headers=" + headers + ", successful=" + successful + "]";
    }

  }
}
