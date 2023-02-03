package com.universe.crypto;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.asn1.pkcs.RSAPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * @author Nick Liu
 * @date 2023/2/2
 */
public class RsaKeyTransformationUtils {

	private static final String ALGORITHM_NAME = "RSA";
	private static final Encoder BASE64_ENCODER = Base64.getEncoder();
	private static final Decoder BASE64_DECODER = Base64.getDecoder();

	static {
		// 必须创建Bouncy Castle提供者
		Security.addProvider(new BouncyCastleProvider());
	}

	public static void main(String[] args) throws Exception {
		String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMPwibA1Z5saQuLsSnz1edglHJFiXzQZ5FxrHVLqiZHzIp176UjjGA+EeJEo+Nt9BiOGpeA0npycKiZtfgXQTND0gz87X3btzVcuB1w6MlcXh7OgzMtukdmzMx8hh7Sc2N6FhCSJO2FJYcFMTWqhbdj5TQZY0JZ7uQCigPLVR2VNAgMBAAECgYALBGppiwcxVG+wXMLvdcoNGkEZ96MMxevuOmOitudXWol2u3HplENVFAjHeLtNdCE1PCv1iF/mxG0mTf/JCeZXvETXlxTPF5o14uUvvEjqLKuvGRR/4D9NLd6SRygTvHO3itQzgOdoEFhWEjEKsj8R8R8K5DtrnCZX0GW836BVgQJBAOdCcR65gGCfNYSDWk1Thhi0rMLjL0RvXraB6MC5X8CklvFbrlGNuMFwHgPSPogZOfZPW3NZ+PtNRxwAKAdMWaECQQDY5sfdZvDlUVG6h+xtEX+I8TF4Btw7+fMFUu4luyo84EouQx2Qvwc8rd/2xo5PiLYQDW/B8Sw5fo3G3T0yoSQtAkAMzG2MQMHtFwKUOdzGiMfUGDOzeXVFOVCpkxj5iYjWFYXRB7znAIvoELdoiLszNwoxKoUqJiGUbttvnkaY2M3hAkBSGsn9XUJDDA1L9rfgcYc9Z0+6h55Gdc8wbLwJPFg4ww5RhMZkTGuI5Kiq2W51XOLOIMf1Oj3rZaR1aroHuEfhAkB3MQ0ms0oYvoZD+dbwjbA7irbNAxOI53+wkXXt2rbE/WgwusDH5bYhjpUfFt5VNFMsXiOnMyInsJ6iMwVG4Sn7";
		String privateKeyInPkcs1 = transformPrivateKeyFromPkcs8ToPkcs1(privateKey);
		String privateKeyInPkcs8 = transformPrivateKeyFromPkcs1ToPkcs8(privateKeyInPkcs1);
		System.out.println(CryptoUtils.signBySHA256WithRSA(privateKeyInPkcs8, "test"));
	}

	/**
	 * 将PKCS1公钥转换为PKCS8公钥
	 * @param pubKeyInPKCS1 PKCS1形式公钥
	 * @return PKCS8形式公钥
	 * @throws Exception
	 */
	public static String transformPubKeyFromPkcs1ToPkcs8(String pubKeyInPKCS1) throws Exception {
		RSAPublicKey rsaPublicKey = RSAPublicKey.getInstance(BASE64_DECODER.decode(pubKeyInPKCS1));
		KeySpec keySpec = new RSAPublicKeySpec(rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());

		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return BASE64_ENCODER.encodeToString(publicKey.getEncoded());
	}

	/**
	 * 将PKCS8公钥转换为PKCS1公钥
	 * @param pubKeyInPKCS8 PKCS8公钥
	 * @return PKCS1公钥
	 */
	public static String transformPubKeyFromPkcs8ToPkcs1(String pubKeyInPKCS8) {
		ASN1Sequence publicKeyASN1Object = ASN1Sequence.getInstance(BASE64_DECODER.decode(pubKeyInPKCS8));
		DERBitString derBitString = (DERBitString) publicKeyASN1Object.getObjectAt(1);
		return BASE64_ENCODER.encodeToString(derBitString.getBytes());
	}

	/**
	 * 将PKCS1私钥转换为PKCS8公钥
	 * @param privateKeyInPKCS1 PKCS1公钥
	 * @return PKCS8公钥
	 * @throws Exception
	 */
	public static String transformPrivateKeyFromPkcs1ToPkcs8(String privateKeyInPKCS1) throws Exception {
		KeySpec keySpec = new PKCS8EncodedKeySpec(BASE64_DECODER.decode(privateKeyInPKCS1));
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return BASE64_ENCODER.encodeToString(privateKey.getEncoded());
	}

	public static String transformPrivateKeyFromPkcs8ToPkcs1(String privateKeyInPKCS8) throws Exception {
		PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(BASE64_DECODER.decode(privateKeyInPKCS8));
		RSAPrivateKey rsaPrivateKey = RSAPrivateKey.getInstance(privateKeyInfo.parsePrivateKey());
		return BASE64_ENCODER.encodeToString(rsaPrivateKey.getEncoded());
	}


}
