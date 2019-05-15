package com.universe.crypto.asymmetric;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class RsaEncryptionExample {

  public static void main(String[] args) throws Exception {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(2048, new SecureRandom());
    KeyPair keyPair = generator.generateKeyPair();

    PublicKey publicKey = keyPair.getPublic();
    PrivateKey privateKey = keyPair.getPrivate();

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    // 公钥必须使用RSAPublicKeySpec或者X509EncodedKeySpec
    KeySpec publicKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
    // 私钥必须使用RSAPrivateCrtKeySpec或者PKCS8EncodedKeySpec
    KeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());

    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generatePublic(publicKeySpec));
    byte[] encoded = cipher.doFinal("liuyalou".getBytes());
    System.out.println(Base64.getEncoder().encodeToString(encoded));

    cipher.init(Cipher.DECRYPT_MODE, keyFactory.generatePrivate(privateKeySpec));
    byte[] decoded = cipher.doFinal(encoded);
    System.out.println(decoded);
  }

}
