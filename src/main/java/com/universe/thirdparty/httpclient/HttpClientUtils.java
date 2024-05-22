package com.universe.thirdparty.httpclient;

import com.universe.thirdparty.fastjson.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 基于HttpClient4.5的网络请求工具类，详细配置可参考：
 * http://hc.apache.org/httpcomponents-client-4.5.x/tutorial/html/index.html
 * @author 刘亚楼
 * @date 2020/6/18
 */
public abstract class HttpClientUtils {

	private static final HttpClient DEFAULT_CLIENT;

	static {
		// 连接最大空闲时间为5秒，超出5秒后即释放
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(5000, TimeUnit.MILLISECONDS);
		// 最大总连接数(多个路由连接数之和)，默认为20
		connectionManager.setMaxTotal(500);
		// 每个路由(即ip+端口)最大连接数，默认为2
		connectionManager.setDefaultMaxPerRoute(50);
		// 设置连接超时时长为3秒，网络请求超时时长为5秒，从连接池获取连接超时时长为1秒
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(5000).setConnectionRequestTimeout(1000).build();
		DEFAULT_CLIENT = HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig).build();
	}

	/**
	 * 发送get请求
	 * @param url 资源地址
	 * @param headers 请求头
	 * @param params 请求参数
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResp get(String url, Map<String, Object> headers, Map<String, Object> params) throws Exception {
		URIBuilder uriBuilder = new URIBuilder(url);
		if (!CollectionUtils.isEmpty(params)) {
			for (Map.Entry<String, Object> param : params.entrySet()) {
				uriBuilder.setParameter(param.getKey(), String.valueOf(param.getValue()));
			}
		}

		HttpGet httpGet = new HttpGet(uriBuilder.build());
		setHeaders(httpGet, headers);
		return getResponse(httpGet);
	}

	/**
	 * 模拟表单发送post请求
	 * @param url 资源地址
	 * @param headers 请求头
	 * @param params 请求参数
	 * @return
	 * @throws IOException
	 */
	public static HttpClientResp postInHtmlForm(String url, Map<String, Object> headers, Map<String, Object> params) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		setHeaders(httpPost, headers);
		if (!CollectionUtils.isEmpty(params)) {
			List<NameValuePair> formParams = new ArrayList<>();
			for (Map.Entry<String, Object> param : params.entrySet()) {
				formParams.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));
		}

		return getResponse(httpPost);
	}

	/**
	 * 发送post请求，请求参数格式为json
	 * @param url 资源地址
	 * @param headers 请求头
	 * @param jsonStr 请求参数json字符串
	 * @return
	 * @throws IOException
	 */
	public static HttpClientResp postInJson(String url, Map<String, Object> headers, String jsonStr) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		setHeaders(httpPost, headers);
		httpPost.setEntity(new StringEntity(jsonStr, ContentType.APPLICATION_JSON));
		return getResponse(httpPost);
	}

	public static InputStream download(String url) throws Exception {
		URIBuilder uriBuilder = new URIBuilder(url);
		HttpGet httpGet = new HttpGet(uriBuilder.build());
		try (CloseableHttpResponse response = (CloseableHttpResponse) DEFAULT_CLIENT.execute(httpGet, HttpClientContext.create())) {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode >= HttpStatus.SC_OK && statusCode < HttpStatus.SC_MULTIPLE_CHOICES) {
				HttpEntity httpEntity = response.getEntity();
				byte[] content = IOUtils.toByteArray(httpEntity.getContent());
				return new ByteArrayInputStream(content);
			}
			throw new IllegalStateException("Invalid response code!");
		}
	}

	public static void setHeaders(AbstractHttpMessage message, Map<String, Object> headers) {
		if (!CollectionUtils.isEmpty(headers)) {
			for (Map.Entry<String, Object> header : headers.entrySet()) {
				message.setHeader(header.getKey(), String.valueOf(header.getValue()));
			}
		}
	}

	private static HttpClientResp getResponse(HttpRequestBase request) throws IOException {
		try (CloseableHttpResponse response = (CloseableHttpResponse) DEFAULT_CLIENT.execute(request, HttpClientContext.create())) {
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
				resp.setRespContent(EntityUtils.toString(httpEntity, Consts.UTF_8));
				if (httpEntity.getContentEncoding() != null) {
					resp.setContentEncoding(httpEntity.getContentEncoding().getValue());
				}
			}

			return resp;
		}

	}

	public static class HttpClientResp {

		private String respContent;
		private long contentLength;
		private String contentType;
		private String contentEncoding;
		private Map<String, String> headers;
		private boolean successful;

		public String getRespContent() {
			return respContent;
		}

		public void setRespContent(String respContent) {
			this.respContent = respContent;
		}

		public long getContentLength() {
			return contentLength;
		}

		public void setContentLength(long contentLength) {
			this.contentLength = contentLength;
		}

		public String getContentType() {
			return contentType;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		public String getContentEncoding() {
			return contentEncoding;
		}

		public void setContentEncoding(String contentEncoding) {
			this.contentEncoding = contentEncoding;
		}

		public Map<String, String> getHeaders() {
			return headers;
		}

		public void setHeaders(Map<String, String> headers) {
			this.headers = headers;
		}

		public boolean isSuccessful() {
			return successful;
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
