package com.vroomerp.security;

import com.vroomerp.model.TUser;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {

	private static final String SECRET_KEY = "vroomerpSuperSecretKey123";

	public static String generateToken(TUser user) {
		return Jwts.builder()
				.setSubject(String.valueOf(user.getUserId())) // sub = ID utente
				.claim("email", user.getEmail())
				.claim("nome", user.getNome())
				.claim("cognome", user.getCognome())
				// .claim("ruolo", user.getRuolo().getNome()) // opzionale se gestisci ruoli
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 ora
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}

	public static String validateToken(String token) throws Exception {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
