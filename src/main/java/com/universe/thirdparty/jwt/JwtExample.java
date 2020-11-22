package com.universe.thirdparty.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.universe.jdkapi.jdk8.datetime.DateTimeUtils;
import com.universe.jdkapi.jdk8.datetime.DateTimeUtils.Pattern;

import java.time.LocalDateTime;

/**
 * @author 刘亚楼
 * @date 2020/11/10
 */
public class JwtExample {

	public static void main(String[] args) throws InterruptedException {
		Algorithm algorithm = Algorithm.HMAC256("secret");
		String token = JWT.create()
			.withIssuer("universe")
			.withSubject("Nick")
			.withExpiresAt(DateTimeUtils.localDateTimeToDate(LocalDateTime.now().plusSeconds(2)))
			.sign(algorithm);
		System.out.println("生成的token为：" + token);

		Thread.sleep(2000);
		JWTVerifier verifier = JWT.require(algorithm)
			.withIssuer("universe")
			.withSubject("Nick")
			.acceptLeeway(1)// 可接受的时间偏差，单位为1秒，等于多1秒过期时间
			.build();
		// 这里能验证成功，因为允许1秒钟的偏差
		DecodedJWT decodedJWT = verifier.verify(token);
		System.out.println(decodedJWT.getIssuer());
		System.out.println(decodedJWT.getSubject());
		System.out.println(DateTimeUtils.format(decodedJWT.getExpiresAt(), Pattern.YEAR_TO_SECOND_WITH_STRIKE));
	}
}
