package com.universe.thirdparty.okhttp;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.universe.thirdparty.fastjson.CollectionUtils;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpUtils {

  public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
  public static final MediaType OCTET_STREAM = MediaType.get("application/octet-stream");
  /**
   * 超时、读、写时长
   */
  private static final long TIMEOUT_MILLIS = 2000;

  private static final Builder DEFAULT_CLIENT_BUILDER = new OkHttpClient.Builder();

  static {
    DEFAULT_CLIENT_BUILDER.readTimeout(Duration.ofMillis(TIMEOUT_MILLIS));
    DEFAULT_CLIENT_BUILDER.writeTimeout(Duration.ofMillis(TIMEOUT_MILLIS));
    DEFAULT_CLIENT_BUILDER.connectTimeout(Duration.ofMillis(TIMEOUT_MILLIS));
  }

  public static OkHttpClient getOkHttpClient() {
    return DEFAULT_CLIENT_BUILDER.build();
  }

  public static synchronized OkHttpClient getOkHttpClient(long timeoutInMillis) {
    DEFAULT_CLIENT_BUILDER.readTimeout(Duration.ofMillis(timeoutInMillis));
    DEFAULT_CLIENT_BUILDER.writeTimeout(Duration.ofMillis(timeoutInMillis));
    DEFAULT_CLIENT_BUILDER.connectTimeout(Duration.ofMillis(timeoutInMillis));
    return DEFAULT_CLIENT_BUILDER.build();
  }

  public static OkHttpResp sendGet(String url, Map<String, Object> reqHeaders, Map<String, Object> params) throws IOException {
    return sendGet(url, reqHeaders, params, TIMEOUT_MILLIS);
  }

  public static OkHttpResp sendGet(String url, Map<String, Object> reqHeaders, Map<String, Object> params, long timeoutInMillis)
      throws IOException {
    OkHttpClient client = getOkHttpClient(timeoutInMillis);
    HttpUrl.Builder urlBuilder = HttpUrl.get(url).newBuilder();
    // 拼接参数
    if (!CollectionUtils.isEmpty(params)) {
      params.forEach((key, value) -> {
        urlBuilder.addEncodedQueryParameter(key, String.valueOf(value));
      });
    }

    HttpUrl httpUrl = urlBuilder.build();
    Request.Builder reqBuilder = new Request.Builder().url(httpUrl);
    addHeaders(reqHeaders, reqBuilder);
    Request request = reqBuilder.get().build();

    return getResponse(client, request);
  }

  public static OkHttpResp sendPostInHtmlForm(String url, Map<String, Object> headers, Map<String, Object> params) throws IOException {
    return sendPostInHtmlForm(url, headers, params, TIMEOUT_MILLIS);
  }

  public static OkHttpResp sendPostInHtmlForm(String url, Map<String, Object> reqHeaders, Map<String, Object> params, long timeoutInMillis)
      throws IOException {
    OkHttpClient client = getOkHttpClient(timeoutInMillis);
    Request.Builder reqBuilder = new Request.Builder().url(url);
    addHeaders(reqHeaders, reqBuilder);

    FormBody.Builder formBuilder = new FormBody.Builder(Charset.forName("UTF-8"));
    FormBody formBody = formBuilder.build();

    if (!CollectionUtils.isEmpty(params)) {
      params.forEach((paramName, paramValue) -> {
        formBuilder.add(paramName, String.valueOf(paramValue));
      });
    }

    Request request = reqBuilder.post(formBody).build();
    return getResponse(client, request);
  }

  public static OkHttpResp sendPostInJsonFormat(String url, Map<String, Object> reqHeaders, Map<String, Object> params) throws IOException {
    return sendPostInJsonFormat(url, reqHeaders, params, TIMEOUT_MILLIS);
  }

  public static OkHttpResp sendPostInJsonFormat(String url, Map<String, Object> reqHeaders, Map<String, Object> params,
      long timeoutInMillis) throws IOException {
    OkHttpClient client = getOkHttpClient(timeoutInMillis);
    Request.Builder reqBuilder = new Request.Builder().url(url);
    addHeaders(reqHeaders, reqBuilder);

    RequestBody requestBody = RequestBody.create(JSON, JSONObject.toJSONString(params));
    Request request = reqBuilder.post(requestBody).build();

    return getResponse(client, request);
  }

  public static OkHttpResp uploadFile(String url, List<MultipartFile> files) throws IOException {
    return uploadFile(url, files, TIMEOUT_MILLIS);
  }

  public static OkHttpResp uploadFile(String url, List<MultipartFile> files, long timeoutInMillis) throws IOException {
    OkHttpClient client = getOkHttpClient(timeoutInMillis);
    MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
    multiBuilder.setType(MultipartBody.FORM);
    if (!CollectionUtils.isEmpty(files)) {
      files.forEach(multipartFile -> {
        String fieldName = multipartFile.getFieldName();
        String fileName = multipartFile.getFileName();
        byte[] content = multipartFile.getContent();
        multiBuilder.addFormDataPart(fieldName, fileName, RequestBody.create(OCTET_STREAM, content));
      });
    }

    MultipartBody requestBody = multiBuilder.build();
    Request request = new Request.Builder().url(url).post(requestBody).build();
    return getResponse(client, request);
  }

  public static byte[] downloadFile(String url) throws IOException {
    return downloadFile(url, TIMEOUT_MILLIS);
  }

  public static byte[] downloadFile(String url, long timeoutInMillis) throws IOException {
    OkHttpClient client = getOkHttpClient(timeoutInMillis);
    Request request = new Request.Builder().url(url).get().build();
    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        return null;
      }

      return response.body().bytes();
    } catch (IOException e) {
      throw e;
    }
  }

  private static OkHttpResp getResponse(OkHttpClient client, Request request) throws IOException {
    OkHttpResp resp = new OkHttpResp();
    // 确保Response和ResponseBody关闭
    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        resp.setSuccessful(false);
      }
      ResponseBody body = response.body();
      resp.setSuccessful(true);
      resp.setRespStr(body.string());
      resp.setRespHeaders(response.headers());
      resp.setContentType(body.contentType());
      resp.setContentLength(body.contentLength());
      return resp;
    } catch (IOException e) {
      throw e;
    }
  }

  private static void addHeaders(Map<String, Object> reqHeaders, Request.Builder reqBuilder) {
    if (CollectionUtils.isEmpty(reqHeaders)) {
      return;
    }

    reqHeaders.forEach((headerName, headerValue) -> {
      reqBuilder.addHeader(headerName, String.valueOf(headerValue));
    });
  }

  public static class MultipartFile {

    /**
     * 文件域名，相当于表单中文件域名
     */
    private String fieldName;
    private String fileName;
    private byte[] content;

    public MultipartFile() {
    }

    public MultipartFile(String fieldName, String fileName, byte[] content) {
      this.fieldName = fieldName;
      this.fileName = fileName;
      this.content = content;
    }

    public String getFieldName() {
      return fieldName;
    }

    public void setFieldName(String fieldName) {
      this.fieldName = fieldName;
    }

    public String getFileName() {
      return fileName;
    }

    public void setFileName(String fileName) {
      this.fileName = fileName;
    }

    public byte[] getContent() {
      return content;
    }

    public void setContent(byte[] content) {
      this.content = content;
    }
  }

  public static class OkHttpResp {

    private String respStr;
    private Headers respHeaders;
    private MediaType contentType;
    private long contentLength;
    private boolean successful;

    public String getRespStr() {
      return respStr;
    }

    public Headers getRespHeaders() {
      return respHeaders;
    }

    public MediaType getContentType() {
      return contentType;
    }

    public long getContentLength() {
      return contentLength;
    }

    public boolean isSuccessful() {
      return successful;
    }

    public void setRespStr(String respStr) {
      this.respStr = respStr;
    }

    public void setRespHeaders(Headers respHeaders) {
      this.respHeaders = respHeaders;
    }

    public void setContentType(MediaType contentType) {
      this.contentType = contentType;
    }

    public void setContentLength(long contentLength) {
      this.contentLength = contentLength;
    }

    public void setSuccessful(boolean successful) {
      this.successful = successful;
    }

    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

  }
}