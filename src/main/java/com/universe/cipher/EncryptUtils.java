package com.universe.cipher;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtils {

  private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
  private static final Encoder BASE64_ENCODER = Base64.getEncoder();
  private static final Decoder BASE64_DECODER = Base64.getDecoder();

  public static String generateSecretKey(Algorithm algorithm) throws NoSuchAlgorithmException {
    KeyGenerator generator = KeyGenerator.getInstance(algorithm.getName());
    generator.init(algorithm.getKeySize());
    SecretKey secretKey = generator.generateKey();
    return BASE64_ENCODER.encodeToString(secretKey.getEncoded());
  }

  public static String encryptByAES(String secretKey, String cleartext) throws Exception {
    return encrypt(secretKey, cleartext, Algorithm.AES);
  }

  public static String decryptyByAES(String secretKey, String ciphertext) throws Exception {
    return decrypt(secretKey, ciphertext, Algorithm.AES);
  }

  public static String encryptByDES(String secretKey, String cleartext) throws Exception {
    return encrypt(secretKey, cleartext, Algorithm.DES);
  }

  public static String decryptByDES(String secretKey, String ciphertext) throws Exception {
    return decrypt(secretKey, ciphertext, Algorithm.DES);
  }

  public static String encrypt(String secretKey, String cleartext, Algorithm algorithm) throws Exception {
    SecretKey key = decodeSecretKey(secretKey, algorithm);
    Cipher cipher = Cipher.getInstance(algorithm.getName());
    cipher.init(Cipher.ENCRYPT_MODE, key);
    byte[] msg = cipher.doFinal(cleartext.getBytes(DEFAULT_CHARSET));
    return BASE64_ENCODER.encodeToString(msg);
  }

  public static String decrypt(String secretKey, String ciphertext, Algorithm algorithm) throws Exception {
    SecretKey key = decodeSecretKey(secretKey, algorithm);
    Cipher cipher = Cipher.getInstance(algorithm.getName());
    cipher.init(Cipher.DECRYPT_MODE, key);
    byte[] decrypted = cipher.doFinal(BASE64_DECODER.decode(ciphertext));
    return new String(decrypted, DEFAULT_CHARSET);
  }

  /**
   * 将密钥进行Base64位解码，重新生成SecretKey实例
   * @param secretKey
   * @param algorithm
   * @return
   */
  private static SecretKey decodeSecretKey(String secretKey, Algorithm algorithm) {
    byte[] key = BASE64_DECODER.decode(secretKey);
    return new SecretKeySpec(key, algorithm.getName());
  }

  public static enum Algorithm {
    AES("AES", 128), DES("DES", 56), RSA("RSA", 2048);

    private String name;
    private int keySize;

    private Algorithm(String name, int keySize) {
      this.name = name;
      this.keySize = keySize;
    }

    public String getName() {
      return name;
    }

    public int getKeySize() {
      return keySize;
    }

  }

  public static void main(String[] args) throws Exception {
    String secret = generateSecretKey(Algorithm.DES);
    String ciphertext = encryptByDES(secret, "刘亚楼");
    String cleartext = decryptByDES(secret, ciphertext);
    System.out.println(ciphertext);
    System.out.println(cleartext);

  }

}
