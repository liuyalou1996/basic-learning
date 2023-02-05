package com.universe.crypto;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.asn1.pkcs.RSAPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
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
public class AsymmetricKeyUtils {

	private static final String ALGORITHM_NAME = "RSA";
	private static final Encoder BASE64_ENCODER = Base64.getEncoder();
	private static final Decoder BASE64_DECODER = Base64.getDecoder();

	static {
		// 必须创建Bouncy Castle提供者
		Security.addProvider(new BouncyCastleProvider());
	}

	public static void main(String[] args) throws Exception {
		String privateKey =
			"MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMPwibA1Z5saQuLsSnz1edglHJFiXzQZ5FxrHVLqiZHzIp176UjjGA+EeJEo+Nt9BiOGpeA0npycKiZtfgXQTND0gz87X3btzVcuB1w6MlcXh7OgzMtukdmzMx8hh7Sc2N6FhCSJO2FJYcFMTWqhbdj5TQZY0JZ7uQCigPLVR2VNAgMBAAECgYALBGppiwcxVG+wXMLvdcoNGkEZ96MMxevuOmOitudXWol2u3HplENVFAjHeLtNdCE1PCv1iF/mxG0mTf/JCeZXvETXlxTPF5o14uUvvEjqLKuvGRR/4D9NLd6SRygTvHO3itQzgOdoEFhWEjEKsj8R8R8K5DtrnCZX0GW836BVgQJBAOdCcR65gGCfNYSDWk1Thhi0rMLjL0RvXraB6MC5X8CklvFbrlGNuMFwHgPSPogZOfZPW3NZ+PtNRxwAKAdMWaECQQDY5sfdZvDlUVG6h+xtEX+I8TF4Btw7+fMFUu4luyo84EouQx2Qvwc8rd/2xo5PiLYQDW/B8Sw5fo3G3T0yoSQtAkAMzG2MQMHtFwKUOdzGiMfUGDOzeXVFOVCpkxj5iYjWFYXRB7znAIvoELdoiLszNwoxKoUqJiGUbttvnkaY2M3hAkBSGsn9XUJDDA1L9rfgcYc9Z0+6h55Gdc8wbLwJPFg4ww5RhMZkTGuI5Kiq2W51XOLOIMf1Oj3rZaR1aroHuEfhAkB3MQ0ms0oYvoZD+dbwjbA7irbNAxOI53+wkXXt2rbE/WgwusDH5bYhjpUfFt5VNFMsXiOnMyInsJ6iMwVG4Sn7";
		String privateKeyInPkcs1 = transformPrivateKeyFromPkcs8ToPkcs1(privateKey);
		String privateKeyInPkcs8 = transformPrivateKeyFromPkcs1ToPkcs8(privateKeyInPkcs1);

		String privateKeyInPkcs8AsPem = formatKeyAsPemString(KeyFormat.RSA_PRIVATE_KEY_PKCS8, privateKeyInPkcs1);
		System.out.println(privateKeyInPkcs8AsPem);
		System.out.println(extractKeyFromPemString(privateKeyInPkcs8AsPem));
		System.out.println(CryptoUtils.signBySHA256WithRSA(privateKeyInPkcs8, "test"));
	}

	/**
	 * 格式化密钥为标准Pem格式
	 * @param keyFormat 密钥格式，参考{@link KeyFormat}
	 * @param asymmetricKey 非对称密钥
	 * @return .pem格式密钥字符串
	 * @throws IOException
	 */
	public static String formatKeyAsPemString(KeyFormat keyFormat, String asymmetricKey) throws IOException {
		byte[] keyInBytes = BASE64_DECODER.decode(asymmetricKey);
		PemObject pemObject = new PemObject(keyFormat.getName(), keyInBytes);
		try (StringWriter stringWriter = new StringWriter()) {
			PemWriter pemWriter = new PemWriter(stringWriter);
			pemWriter.writeObject(pemObject);
			pemWriter.flush();
			return stringWriter.toString();
		}
	}

	/**
	 * 从标准Pem格式中提取密钥
	 * @param asymmetricKeyAsPem
	 * @return 无---BEGIN---和---END---密钥字符串
	 * @throws IOException
	 */
	public static String extractKeyFromPemString(String asymmetricKeyAsPem) throws IOException {
		try (PemReader pemReader = new PemReader(new StringReader(asymmetricKeyAsPem))) {
			PemObject pemObject = pemReader.readPemObject();
			return BASE64_ENCODER.encodeToString(pemObject.getContent());
		}
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

	/**
	 * 将PKCS1私钥转换为PKCS8私钥
	 * @param privateKeyInPKCS8 PKCS8私钥
	 * @return PKCS1私钥
	 * @throws Exception
	 */
	public static String transformPrivateKeyFromPkcs8ToPkcs1(String privateKeyInPKCS8) throws Exception {
		PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(BASE64_DECODER.decode(privateKeyInPKCS8));
		RSAPrivateKey rsaPrivateKey = RSAPrivateKey.getInstance(privateKeyInfo.parsePrivateKey());
		return BASE64_ENCODER.encodeToString(rsaPrivateKey.getEncoded());
	}

	public enum KeyFormat {
		/**
		 * PKCS1格式RSA私钥
		 */
		RSA_PRIVATE_KEY_PKCS1("RSA PRIVATE KEY"),
		/**
		 * PKCS8格式RSA私钥
		 */
		RSA_PRIVATE_KEY_PKCS8("PRIVATE KEY"),
		/**
		 * PKCS1格式RSA公钥
		 */
		RSA_PUBLIC_KEY_PKCS1("RSA PUBLIC KEY"),
		/**
		 * PKCS8格式RSA公钥
		 */
		RSA_PUBLIC_KEY_PKCS8("PUBLIC KEY");

		private String name;

		KeyFormat(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

}
