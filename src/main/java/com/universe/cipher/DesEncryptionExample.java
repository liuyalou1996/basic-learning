package com.universe.cipher;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesEncryptionExample {

  public static void main(String[] args) throws Exception {
    String key = "12345678";
    DESKeySpec keySpec = new DESKeySpec(key.getBytes());
    SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
    SecretKey secretKey = factory.generateSecret(keySpec);

    Cipher cipher = Cipher.getInstance("DES");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encoded = cipher.doFinal("liuyalou".getBytes());
    String encodedStr = Base64.getEncoder().encodeToString(encoded);

    byte[] decoded = Base64.getDecoder().decode(encodedStr);
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] decodedStr = cipher.doFinal(decoded);
    System.out.println(new String(decodedStr));
  }
}
