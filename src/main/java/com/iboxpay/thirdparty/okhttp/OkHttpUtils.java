package com.iboxpay.thirdparty.okhttp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.iboxpay.thirdparty.fastjson.CollectionUtils;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpUtils {

  public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

  public static final MediaType OCTET_STREAM = MediaType.get("application/octet-stream");

  /**
   * 请求带的UA
   */
  public static final String USER_AGENT =
      "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";

  /**
   * 超时、读、写时长
   */
  public static final int TIMEOUT = 10;

  public static OkHttpClient getOkHttpClient() {
    return getOkHttpClient(TIMEOUT);
  }

  public static OkHttpClient getOkHttpClient(int timeout) {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
    builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
    builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
    return builder.build();
  }

  public static OkHttpResp sendGet(String url, Map<String, Object> reqHeaders, Map<String, Object> params)
      throws IOException {
    return sendGet(url, reqHeaders, params, TIMEOUT);
  }

  /**
   * get请求
   * @param url 请求地址
   * @param reqHeaders 请求头
   * @param params 请求参数
   * @param timeout 超时时长
   * @return
   * @throws IOException
   */
  public static OkHttpResp sendGet(String url, Map<String, Object> reqHeaders, Map<String, Object> params, int timeout)
      throws IOException {
    if (StringUtils.isBlank(url)) {
      throw new IllegalArgumentException("request url can not be null");
    }

    OkHttpClient client = getOkHttpClient(timeout);
    HttpUrl.Builder urlBuilder = HttpUrl.get(url).newBuilder();
    // 拼接参数
    if (!CollectionUtils.isEmpty(params)) {
      for (Entry<String, Object> param : params.entrySet()) {
        Object value = param.getValue();
        if (value == null) {
          urlBuilder.addEncodedQueryParameter(param.getKey(), null);
        } else {
          urlBuilder.addEncodedQueryParameter(param.getKey(), String.valueOf(param.getValue()));
        }
      }
    }

    HttpUrl httpUrl = urlBuilder.build();
    Request.Builder reqBuilder = new Request.Builder().url(httpUrl);
    // 增加请求头
    addHeaders(reqHeaders, reqBuilder);
    Request request = reqBuilder.get().build();
    return getResponse(client, request);
  }

  public static OkHttpResp sendPostInHtmlForm(String url, Map<String, Object> headers, Map<String, Object> params)
      throws IOException {
    return sendPostInHtmlForm(url, headers, params, TIMEOUT);
  }

  /**
   * 发送post请求，模仿html表单
   * @param url 请求地址
   * @param reqHeaders 请求头
   * @param params 请求参数
   * @param timeout 超时时长
   * @return
   * @throws IOException
   */
  public static OkHttpResp sendPostInHtmlForm(String url, Map<String, Object> reqHeaders, Map<String, Object> params,
      int timeout) throws IOException {
    if (StringUtils.isBlank(url)) {
      throw new IllegalArgumentException("request url can not be null");
    }

    OkHttpClient client = getOkHttpClient(timeout);
    Request.Builder reqBuilder = new Request.Builder().url(url);
    // 增加请求头
    addHeaders(reqHeaders, reqBuilder);
    FormBody.Builder formBuilder = new FormBody.Builder(Charset.forName("UTF-8"));
    FormBody formBody = formBuilder.build();
    // 添加请求参数
    if (!CollectionUtils.isEmpty(params)) {
      for (Map.Entry<String, Object> param : params.entrySet()) {
        Object obj = param.getValue();
        if (obj != null) {
          formBuilder.add(param.getKey(), String.valueOf(param.getValue()));
        } else {
          formBuilder.add(param.getKey(), null);
        }
      }
    }

    Request request = reqBuilder.post(formBody).build();
    return getResponse(client, request);
  }

  public static OkHttpResp sendPostInJsonFormat(String url, Map<String, Object> reqHeaders, Map<String, Object> params)
      throws IOException {
    return sendPostInJsonFormat(url, reqHeaders, params, TIMEOUT);
  }

  /**
   * 发送post请求，请求参数格式为json格式
   * @param url 请求地址
   * @param reqHeaders 请求头
   * @param params 请求参数
   * @param timeout 超时时长
   * @return
   * @throws IOException
   */
  public static OkHttpResp sendPostInJsonFormat(String url, Map<String, Object> reqHeaders, Map<String, Object> params,
      int timeout) throws IOException {
    if (StringUtils.isBlank(url)) {
      throw new IllegalArgumentException("request url can not be null");
    }

    OkHttpClient client = getOkHttpClient(timeout);
    Request.Builder reqBuilder = new Request.Builder().url(url);
    // 添加请求头
    addHeaders(reqHeaders, reqBuilder);
    // 添加请求参数
    RequestBody requestBody = RequestBody.create(JSON, JSONObject.toJSONString(params));
    if (CollectionUtils.isEmpty(params)) {
      requestBody = RequestBody.create(JSON, "");
    }

    Request request = reqBuilder.post(requestBody).build();
    return getResponse(client, request);
  }

  public static OkHttpResp uploadFile(String url, List<MultipartFile> files) throws IOException {
    return uploadFile(url, files, TIMEOUT);
  }

  /**
   * 上传文件
   * @param url 请求地址
   * @param files 文件列表
   * @param timeout 超时时长
   * @return
   * @throws IOException
   */
  public static OkHttpResp uploadFile(String url, List<MultipartFile> files, int timeout) throws IOException {
    if (StringUtils.isBlank(url)) {
      throw new IllegalArgumentException("request url can not be null");
    }

    OkHttpClient client = getOkHttpClient(timeout);
    MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
    multiBuilder.setType(MultipartBody.FORM);
    if (!CollectionUtils.isEmpty(files)) {
      for (MultipartFile multipartFile : files) {
        String fieldName = multipartFile.getFieldName();
        String fileName = multipartFile.getFileName();
        byte[] content = multipartFile.getContent();
        multiBuilder.addFormDataPart(fieldName, fileName, RequestBody.create(OCTET_STREAM, content));
      }
    }

    MultipartBody requestBody = multiBuilder.build();
    Request request = new Request.Builder().url(url).addHeader("User-Agent", USER_AGENT).post(requestBody).build();
    return getResponse(client, request);
  }

  public static byte[] downloadFile(String url) throws IOException {
    return downloadFile(url, TIMEOUT);
  }

  /**
   * 下载文件
   * @param url 请求地址
   * @param timeout 超时时长
   * @return
   * @throws IOException
   */
  public static byte[] downloadFile(String url, int timeout) throws IOException {
    if (StringUtils.isBlank(url)) {
      throw new IllegalArgumentException("request url can not be null");
    }

    OkHttpClient client = getOkHttpClient(timeout);
    Request request = new Request.Builder().url(url).addHeader("User-Agent", USER_AGENT).get().build();
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
      resp.setByteStream(body.byteStream());
      resp.setBytes(body.bytes());
      resp.setRespHeaders(response.headers());
      resp.setContentType(body.contentType());
      resp.setContentLength(body.contentLength());
      return resp;
    } catch (IOException e) {
      throw e;
    }
  }

  private static void addHeaders(Map<String, Object> reqHeaders, Request.Builder reqBuilder) {
    reqBuilder.addHeader("User-Agent", USER_AGENT);
    if (!CollectionUtils.isEmpty(reqHeaders)) {
      for (Entry<String, Object> reqHeader : reqHeaders.entrySet()) {
        Object value = reqHeader.getValue();
        if (value != null) {
          reqBuilder.addHeader(reqHeader.getKey(), String.valueOf(reqHeader.getValue()));
        } else {
          reqBuilder.addHeader(reqHeader.getKey(), null);
        }

      }
    }
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
    private InputStream byteStream;
    private byte[] bytes;
    private Headers respHeaders;
    private MediaType contentType;
    private long contentLength;
    private boolean successful;

    public String getRespStr() {
      return respStr;
    }

    public InputStream getByteStream() {
      return byteStream;
    }

    public byte[] getBytes() {
      return bytes;
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

    public void setByteStream(InputStream byteStream) {
      this.byteStream = byteStream;
    }

    public void setBytes(byte[] bytes) {
      this.bytes = bytes;
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
      return "OkHttpResp [respStr=" + respStr + ", byteStream=" + byteStream + ", bytes=" + Arrays.toString(bytes)
          + ", respHeaders=" + respHeaders + ", contentType=" + contentType + ", contentLength=" + contentLength
          + ", successful=" + successful + "]";
    }

  }
}