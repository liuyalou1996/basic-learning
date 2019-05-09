package com.universe.cipher;

import java.util.Base64;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AesEncyptionExample {

  public static void main(String[] args) throws Exception {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    // 种子必须是无法预测的
    String key = UUID.randomUUID().toString().replaceAll("-", "");
    keyGenerator.init(128);
    SecretKey secretKey = keyGenerator.generateKey();

    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encoded = cipher.doFinal("liuyalou".getBytes());
    String encodedStr = Base64.getEncoder().encodeToString(encoded);

    byte[] decoded = Base64.getDecoder().decode(encodedStr);
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] decodedStr = cipher.doFinal(decoded);
    System.out.println(new String(decodedStr));
  }

}
