package com.universe.crypto.asymmetric;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Base64;

public class DsaSignExample {

  public static void main(String[] args) throws Exception {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("DSA");
    generator.initialize(1024, new SecureRandom());
    KeyPair keyPair = generator.generateKeyPair();

    PublicKey publicKey = keyPair.getPublic();
    PrivateKey privateKey = keyPair.getPrivate();

    Signature signature = Signature.getInstance("SHA1withDSA");
    signature.initSign(privateKey, new SecureRandom());
    signature.update("liuyalou".getBytes());
    // 签名
    byte[] signatureInBytes = signature.sign();
    String signatureInStr = Base64.getEncoder().encodeToString(signatureInBytes);
    System.out.println(signatureInStr);

    signature.initVerify(publicKey);
    signature.update("liuyalou".getBytes());
    boolean isValid = signature.verify(Base64.getDecoder().decode(signatureInStr));
    System.out.println(isValid);

  }
}
