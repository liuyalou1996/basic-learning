package com.universe.jdkapi.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class IpUtils {

  private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);

  /**
   * 经过Http代理或负载均衡器会添加
   */
  private static final String X_FORWARDED_FOR = "x-forwarded-for";

  /**
   * apache http服务器代理
   */
  private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
  private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

  /**
   * Nginx反向代理
   */
  private static final String X_REAL_IP = "X-Real-IP";

  /**
   * 其它服务器
   */
  private static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
  private static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

  public static String getClientIp(HttpServletRequest request) {
    String ip = request.getHeader(X_FORWARDED_FOR);
    if (isIpInvalid(ip)) {
      ip = request.getHeader(PROXY_CLIENT_IP);
    }

    if (isIpInvalid(ip)) {
      ip = request.getHeader(WL_PROXY_CLIENT_IP);
    }

    if (isIpInvalid(ip)) {
      ip = request.getHeader(PROXY_CLIENT_IP);
    }

    if (isIpInvalid(ip)) {
      ip = request.getHeader(HTTP_CLIENT_IP);
    }

    if (isIpInvalid(ip)) {
      ip = request.getHeader(HTTP_X_FORWARDED_FOR);
    }

    if (isIpInvalid(ip)) {
      ip = request.getHeader(X_REAL_IP);
    }

    if (isIpInvalid(ip)) {
      ip = request.getRemoteAddr();
    }

    if (ip.contains(",")) {
      // 多级反向代理取第一个
      ip = ip.split(",")[0];
    }

    return ip;

  }

  public static String getLocalHostlIp() {
    String localIp = null;

    try {
      InetAddress inetAddress = InetAddress.getLocalHost();
      localIp = inetAddress.getHostAddress();
    } catch (UnknownHostException e) {
      logger.error("Fail to get local ip: {}", e.getMessage(), e);
    }
    return localIp;
  }

  private static boolean isIpInvalid(String ip) {
    return StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip);
  }

}
