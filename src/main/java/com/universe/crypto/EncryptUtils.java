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
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 支持AES、DES、RSA加密、数字签名以及生成对称密钥和非对称密钥对
 */
public class EncryptUtils {

  private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
  private static final Encoder BASE64_ENCODER = Base64.getEncoder();
  private static final Decoder BASE64_DECODER = Base64.getDecoder();

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
    generator.init(algorithm.getKeySize());
    SecretKey secretKey = generator.generateKey();
    return BASE64_ENCODER.encodeToString(secretKey.getEncoded());
  }

  /**
   * 生成非对称密钥对，目前支持的算法有RSA、DSA
   * @param algorithm
   * @return
   * @throws NoSuchAlgorithmException
   */
  public static AsymmetricKeyPair generateAsymmetricKeyPair(Algorithm algorithm) throws NoSuchAlgorithmException {
    KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm.getName());
    generator.initialize(algorithm.getKeySize());
    KeyPair keyPair = generator.generateKeyPair();
    String publicKey = BASE64_ENCODER.encodeToString(keyPair.getPublic().getEncoded());
    String privateKey = BASE64_ENCODER.encodeToString(keyPair.getPrivate().getEncoded());
    return new AsymmetricKeyPair(publicKey, privateKey);
  }

  public static String encryptByAES(String key, String cleartext) throws Exception {
    return encryptSymmetrically(key, cleartext, Algorithm.AES);
  }

  public static String decryptyByAES(String key, String ciphertext) throws Exception {
    return decryptSymmetrically(key, ciphertext, Algorithm.AES);
  }

  public static String encryptByDES(String key, String cleartext) throws Exception {
    return encryptSymmetrically(key, cleartext, Algorithm.DES);
  }

  public static String decryptByDES(String key, String ciphertext) throws Exception {
    return decryptSymmetrically(key, ciphertext, Algorithm.DES);
  }

  public static String encryptByRSA(String publicKeyText, String cleartext) throws Exception {
    PublicKey publicKey = regeneratePublicKey(publicKeyText, Algorithm.RSA);
    byte[] cleartextInBytes = cleartext.getBytes(DEFAULT_CHARSET);
    byte[] ciphertextInBytes = transform(Algorithm.RSA, Cipher.ENCRYPT_MODE, publicKey, cleartextInBytes);
    return BASE64_ENCODER.encodeToString(ciphertextInBytes);
  }

  public static String decryptByRSA(String privateKeyText, String cipherText) throws Exception {
    PrivateKey privateKey = regeneratePrivateKey(privateKeyText, Algorithm.RSA);
    byte[] ciphertextInBytes = BASE64_DECODER.decode(cipherText);
    byte[] cleartextInBytes = transform(Algorithm.RSA, Cipher.DECRYPT_MODE, privateKey, ciphertextInBytes);
    return new String(cleartextInBytes, DEFAULT_CHARSET);
  }

  /**
   * SHA1签名算法和DSA加密算法结合使用生成数字签名
   * @param privateKeyText
   * @param data
   * @return 数字签名
   * @throws Exception
   */
  public static String signBySHA1WithDSA(String privateKeyText, String data) throws Exception {
    return doSign(privateKeyText, data, Algorithm.DSA, Algorithm.SHA1WithDSA);
  }

  /**
   * SHA1签名算法和RSA加密算法结合使用生成数字签名
   * @param privateKeyText
   * @param data
   * @return 数字签名
   * @throws Exception
   */
  public static String signBySHA1WithRSA(String privateKeyText, String data) throws Exception {
    return doSign(privateKeyText, data, Algorithm.RSA, Algorithm.SHA1WithRSA);
  }

  /**
   * SHA256签名算法和RSA加密算法结合使用生成数字签名
   * @param privateKeyText
   * @param data
   * @return 数字签名
   * @throws Exception
   */
  public static String signBySHA256WithRSA(String privateKeyText, String data) throws Exception {
    return doSign(privateKeyText, data, Algorithm.RSA, Algorithm.SHA256WithRSA);
  }

  /**
   * SHA1签名算法和DSA加密算法检验数字签名
   * @param publicKeyText
   * @param data
   * @param signatureText 数字
   * @return 检验是否成功
   * @throws Exception
   */
  public static boolean verifyBySHA1WithDSA(String publicKeyText, String data, String signatureText) throws Exception {
    return doVerify(publicKeyText, data, signatureText, Algorithm.DSA, Algorithm.SHA1WithDSA);
  }

  /**
   * SHA1签名算法和RSA加密算法检验数字签名
   * @param publicKeyText
   * @param data
   * @param signatureText
   * @return 检验是否成功
   * @throws Exception
   */
  public static boolean verifyBySHA1WithRSA(String publicKeyText, String data, String signatureText) throws Exception {
    return doVerify(publicKeyText, data, signatureText, Algorithm.RSA, Algorithm.SHA1WithRSA);
  }

  /**
   * SHA256签名算法和RSA加密算法检验数字签名
   * @param publicKeyText
   * @param data
   * @param signatureText
   * @return 检验是否成功
   * @throws Exception
   */
  public static boolean verifyBySHA256WithRSA(String publicKeyText, String data, String signatureText)
      throws Exception {
    return doVerify(publicKeyText, data, signatureText, Algorithm.RSA, Algorithm.SHA256WithRSA);
  }

  /**
   * 生成数字签名
   * @param privateKeyText 私钥
   * @param data 传输的数据
   * @param keyAlgorithm 加密算法，见Algorithm中的加密算法
   * @param signatureAlgorithm 签名算法，见Algorithm中的签名算法
   * @return 数字签名
   * @throws Exception
   */
  private static String doSign(String privateKeyText, String data, Algorithm keyAlgorithm, Algorithm signatureAlgorithm)
      throws Exception {
    PrivateKey privateKey = regeneratePrivateKey(privateKeyText, keyAlgorithm);
    // Signature只支持签名算法
    Signature signature = Signature.getInstance(signatureAlgorithm.getName());
    signature.initSign(privateKey);
    signature.update(data.getBytes(DEFAULT_CHARSET));
    byte[] signatureInBytes = signature.sign();
    return BASE64_ENCODER.encodeToString(signatureInBytes);
  }

  /**
   * 数字签名验证
   * @param publicKeyText 公钥 
   * @param data 数据
   * @param signatureText 数字签名
   * @param keyAlgorithm 加密算法，见Algorithm中的加密算法
   * @param signatureAlgorithm 签名算法，见Algorithm中的签名算法
   * @return 校验是否成功
   * @throws Exception
   */
  private static boolean doVerify(String publicKeyText, String data, String signatureText, Algorithm keyAlgorithm,
      Algorithm signatureAlgorithm) throws Exception {
    PublicKey publicKey = regeneratePublicKey(publicKeyText, keyAlgorithm);
    Signature signature = Signature.getInstance(signatureAlgorithm.getName());
    signature.initVerify(publicKey);
    signature.update(data.getBytes(DEFAULT_CHARSET));
    return signature.verify(BASE64_DECODER.decode(signatureText));
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

  private static PublicKey regeneratePublicKey(String publicKeyText, Algorithm algorithm)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] keyInBytes = BASE64_DECODER.decode(publicKeyText);
    KeyFactory keyFactory = getKeyFactory(algorithm);
    // 公钥必须使用RSAPublicKeySpec或者X509EncodedKeySpec
    KeySpec publicKeySpec = new X509EncodedKeySpec(keyInBytes);
    PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
    return publicKey;
  }

  private static PrivateKey regeneratePrivateKey(String key, Algorithm algorithm) throws Exception {
    byte[] keyInBytes = BASE64_DECODER.decode(key);
    KeyFactory keyFactory = getKeyFactory(algorithm);
    // 私钥必须使用RSAPrivateCrtKeySpec或者PKCS8EncodedKeySpec
    KeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyInBytes);
    PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
    return privateKey;
  }

  private static KeyFactory getKeyFactory(Algorithm algorithm) throws NoSuchAlgorithmException {
    KeyFactory keyFactory = KEY_FACTORY_CACHE.get(algorithm);
    if (keyFactory == null) {
      keyFactory = KeyFactory.getInstance(algorithm.getName());
      KEY_FACTORY_CACHE.put(algorithm, keyFactory);
    }

    return keyFactory;
  }

  private static byte[] transform(Algorithm algorithm, int mode, Key key, byte[] msg) throws Exception {
    Cipher cipher = CIPHER_CACHE.get(algorithm);
    // 双空判断，减少上下文切换
    if (cipher == null) {
      synchronized (EncryptUtils.class) {
        if ((cipher = CIPHER_CACHE.get(algorithm)) == null) {
          cipher = Cipher.getInstance(algorithm.getName());
          CIPHER_CACHE.put(algorithm, cipher);
        }

        cipher.init(mode, key);
        return cipher.doFinal(msg);
      }
    }

    synchronized (EncryptUtils.class) {
      cipher.init(mode, key);
      return cipher.doFinal(msg);
    }
  }

  public static enum Algorithm {
    /*
     * 加密算法
     */
    AES("AES", 128), DES("DES", 56), RSA("RSA", 2048), DSA("DSA", 1024),
    /*
     * 签名算法
     */
    SHA1WithDSA("SHA1withDSA", 1024), SHA1WithRSA("SHA1withRSA", 2048), SHA256WithRSA("SHA256withRSA", 2048);

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
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

  }

}
