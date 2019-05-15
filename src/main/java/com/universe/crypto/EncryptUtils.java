package com.universe.crypto;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtils {

  private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
  private static final Encoder BASE64_ENCODER = Base64.getEncoder();
  private static final Decoder BASE64_DECODER = Base64.getDecoder();

  private static final SecureRandom DETAULT_RANDOM_SOURCE = new SecureRandom();

  private static final Map<Algorithm, KeyFactory> KEY_FACTORY_CACHE = new ConcurrentHashMap<>();
  private static final Map<Algorithm, Cipher> CIPHER_CACHE = new HashMap<>();

  /**
   * 生成对称密钥，目前支持的算法有AES、DES
   * @param algorithm
   * @return
   * @throws NoSuchAlgorithmException
   */
  public static String generateSymmetricKey(Algorithm algorithm) throws NoSuchAlgorithmException {
    KeyGenerator generator = KeyGenerator.getInstance(algorithm.getName());
    generator.init(algorithm.getKeySize(), DETAULT_RANDOM_SOURCE);
    SecretKey secretKey = generator.generateKey();
    return BASE64_ENCODER.encodeToString(secretKey.getEncoded());
  }

  /**
   * 生成非对称密钥对
   * @param algorithm
   * @return
   * @throws NoSuchAlgorithmException
   */
  public static AsymmetricKeyPair generateAsymmetricKeyPair(Algorithm algorithm) throws NoSuchAlgorithmException {
    KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm.getName());
    generator.initialize(algorithm.getKeySize(), DETAULT_RANDOM_SOURCE);
    KeyPair keyPair = generator.generateKeyPair();
    String publicKey = BASE64_ENCODER.encodeToString(keyPair.getPublic().getEncoded());
    String privateKey = BASE64_ENCODER.encodeToString(keyPair.getPrivate().getEncoded());
    return new AsymmetricKeyPair(publicKey, privateKey);
  }

  public static String encryptByAES(String secretKey, String cleartext) throws Exception {
    return encryptSymmetrically(secretKey, cleartext, Algorithm.AES);
  }

  public static String decryptyByAES(String secretKey, String ciphertext) throws Exception {
    return decryptSymmetrically(secretKey, ciphertext, Algorithm.AES);
  }

  public static String encryptByDES(String secretKey, String cleartext) throws Exception {
    return encryptSymmetrically(secretKey, cleartext, Algorithm.DES);
  }

  public static String decryptByDES(String secretKey, String ciphertext) throws Exception {
    return decryptSymmetrically(secretKey, ciphertext, Algorithm.DES);
  }

  public static String encryptByRSA(String key, String cleartext) throws Exception {
    byte[] keyInBytes = BASE64_DECODER.decode(key);
    KeyFactory keyFactory = getKeyFactory(Algorithm.RSA);
    // 公钥必须使用RSAPublicKeySpec或者X509EncodedKeySpec
    KeySpec publicKeySpec = new X509EncodedKeySpec(keyInBytes);
    PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
    byte[] cleartextInBytes = cleartext.getBytes(DEFAULT_CHARSET);

    byte[] ciphertextInBytes = transform(Algorithm.RSA, Cipher.ENCRYPT_MODE, publicKey, cleartextInBytes);
    return BASE64_ENCODER.encodeToString(ciphertextInBytes);
  }

  public static String decryptByRSA(String key, String cipherText) throws Exception {
    byte[] keyInBytes = BASE64_DECODER.decode(key);
    KeyFactory keyFactory = getKeyFactory(Algorithm.RSA);
    // 私钥必须使用RSAPrivateCrtKeySpec或者PKCS8EncodedKeySpec
    KeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyInBytes);
    PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
    byte[] ciphertextInBytes = BASE64_DECODER.decode(cipherText);

    byte[] cleartextInBytes = transform(Algorithm.RSA, Cipher.DECRYPT_MODE, privateKey, ciphertextInBytes);
    return new String(cleartextInBytes, DEFAULT_CHARSET);
  }

  private static String encryptSymmetrically(String secretKey, String cleartext, Algorithm algorithm) throws Exception {
    SecretKey key = decodeSymmetricKey(secretKey, algorithm);
    byte[] cleartextInBytes = cleartext.getBytes(DEFAULT_CHARSET);
    byte[] ciphertextInBytes = transform(algorithm, Cipher.ENCRYPT_MODE, key, cleartextInBytes);

    return BASE64_ENCODER.encodeToString(ciphertextInBytes);
  }

  private static String decryptSymmetrically(String secretKey, String ciphertext, Algorithm algorithm)
      throws Exception {
    SecretKey key = decodeSymmetricKey(secretKey, algorithm);
    byte[] ciphertextInBytes = BASE64_DECODER.decode(ciphertext);

    byte[] cleartextInBytes = transform(algorithm, Cipher.DECRYPT_MODE, key, ciphertextInBytes);
    return new String(cleartextInBytes, DEFAULT_CHARSET);
  }

  /**
   * 将密钥进行Base64位解码，重新生成SecretKey实例
   * @param secretKey
   * @param algorithm
   * @return
   */
  private static SecretKey decodeSymmetricKey(String secretKey, Algorithm algorithm) {
    byte[] key = BASE64_DECODER.decode(secretKey);
    return new SecretKeySpec(key, algorithm.getName());
  }

  /**
   * 获取KeyFactory实例
   * @param algorithm
   * @return
   * @throws NoSuchAlgorithmException
   */
  private static KeyFactory getKeyFactory(Algorithm algorithm) throws NoSuchAlgorithmException {
    KeyFactory keyFactory = KEY_FACTORY_CACHE.get(algorithm);
    if (keyFactory == null) {
      keyFactory = KeyFactory.getInstance(algorithm.getName());
      KEY_FACTORY_CACHE.put(algorithm, keyFactory);
    }

    return keyFactory;
  }

  private synchronized static byte[] transform(Algorithm algorithm, int mode, Key key, byte[] msg) throws Exception {
    Cipher cipher = CIPHER_CACHE.get(algorithm);
    if (cipher == null) {
      cipher = Cipher.getInstance(algorithm.getName());
      CIPHER_CACHE.put(algorithm, cipher);
    }

    cipher.init(mode, key);
    return cipher.doFinal(msg);
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

  public static class AsymmetricKeyPair {

    private String publicKey;
    private String privateKey;

    public AsymmetricKeyPair(String publicKey, String privateKey) {
      this.publicKey = publicKey;
      this.privateKey = privateKey;
    }

    public String getPublicKey() {
      return publicKey;
    }

    public String getPrivateKey() {
      return privateKey;
    }

    @Override
    public String toString() {
      return "AsymmetricKey [publicKey=" + publicKey + ", privateKey=" + privateKey + "]";
    }

  }

  public static void main(String[] args) throws Exception {
    String secret = generateSymmetricKey(Algorithm.DES);
    String ciphertext = encryptByDES(secret, "liuyalou");
    String cleartext = decryptByDES(secret, ciphertext);
    System.out.println(ciphertext);
    System.out.println(cleartext);

    AsymmetricKeyPair keyPair = generateAsymmetricKeyPair(Algorithm.RSA);
    String publicKey = keyPair.getPublicKey();
    String privateKey = keyPair.getPrivateKey();
    String cipher = encryptByRSA(publicKey, "liuyalou");
    String clear = decryptByRSA(privateKey, cipher);
    System.out.println(cipher);
    System.out.println(clear);
  }

}
