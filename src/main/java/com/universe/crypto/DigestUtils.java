package com.universe.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigestUtils {

  private static final Logger logger = LoggerFactory.getLogger(DigestUtils.class);

  static class MessageDigestFactory {

    public static MessageDigest getInstance(SignatureAlgorithm algorithm) {
      try {
        switch (algorithm) {
          case MD5:
            return MessageDigest.getInstance("MD5");
          case SHA1:
            return MessageDigest.getInstance("SHA-1");
          case SHA256:
            return MessageDigest.getInstance("SHA-256");
        }
      } catch (NoSuchAlgorithmException e) {
        logger.error(e.getMessage(), e);
      }

      return null;
    }
  }

  public static String digestByMd5(String text) {
    return doDigest(SignatureAlgorithm.MD5, text);
  }

  public static String digestBySha1(String text) {
    return doDigest(SignatureAlgorithm.SHA1, text);
  }

  public static String digestBySha256(String text) {
    return doDigest(SignatureAlgorithm.SHA256, text);
  }

  public static enum SignatureAlgorithm {
    MD5, SHA1, SHA256
  }

  private static String doDigest(SignatureAlgorithm algorithm, String text) {
    MessageDigest instance = MessageDigestFactory.getInstance(algorithm);
    byte[] textInBytes = instance.digest(text.getBytes(StandardCharsets.UTF_8));
    StringBuilder builder = new StringBuilder();
    for (byte b : textInBytes) {
      int value = b & 0xff;
      if (value < 16) {
        builder.append("0");
      }

      builder.append(Integer.toHexString(value).toUpperCase());
    }

    return builder.toString();
  }
}
