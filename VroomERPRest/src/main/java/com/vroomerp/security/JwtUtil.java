package com.vroomerp.security;

import io.jsonwebtoken.*;
import java.util.Date;

public class JwtUtil {

	private static final String SECRET_KEY = "vroomerpSuperSecretKey123";

	public static String generateToken(String email) {
		return Jwts.builder().setSubject(email).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 ora
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public static String validateToken(String token) throws Exception {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	}
}
