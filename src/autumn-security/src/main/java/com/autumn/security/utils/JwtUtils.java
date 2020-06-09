package com.autumn.security.utils;

import java.util.Date;
import java.util.HashMap;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Jwt(Json Web Token) 帮助工具
 * 
 * @author 老码农
 *
 *         2017-11-03 19:32:08
 */
public class JwtUtils {

	/**
	 * 生成 Token
	 * 
	 * @param secret
	 *            密钥
	 * @param jwtClaims
	 *            声明
	 * @param alg
	 *            加密方式
	 * @return
	 */
	public static String generateToken(String secret, JwtClaims jwtClaims, SignatureAlgorithm alg) {
		if (alg == null) {
			alg = SignatureAlgorithm.HS256;
		}
		if (jwtClaims == null) {
			jwtClaims = new JwtClaims();
		}
		if (jwtClaims.getProperties() == null) {
			jwtClaims.setProperties(new HashMap<String, Object>(16));
		}
		return Jwts.builder().setClaims(jwtClaims.getProperties()).setExpiration(jwtClaims.getExpiration())
				.setAudience(jwtClaims.getAudience()).setId(jwtClaims.getId()).setIssuedAt(jwtClaims.getIssuedAt())
				.setIssuer(jwtClaims.getIssuer()).setNotBefore(jwtClaims.getNotBefore())
				.setSubject(jwtClaims.getSubject()).setHeaderParam("typ", "JWT").signWith(alg, secret).compact();

	}

	/**
	 * 通过票据获取声明
	 * 
	 * @param secret
	 *            钥匙
	 * @param token
	 *            票据
	 * @return
	 */
	public static Claims getClaimsFromToken(String secret, String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * 判断令牌是否过期
	 *
	 * @param claims
	 *            声明
	 * @return 是否过期
	 */
	public static boolean isTokenExpired(Claims claims) {
		Date expiration = claims.getExpiration();
		return expiration.before(new Date());
	}
	
	

}
