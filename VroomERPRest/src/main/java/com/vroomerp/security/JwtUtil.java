package com.vroomerp.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

import com.vroomerp.model.TUser;


public class JwtUtil {

    private static final String SECRET_KEY = "vroomerpSuperSecretKey123";

    // ✅ Metodo per generare token JWT
    public static String generateToken(TUser user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getUserId())) // L'ID utente sarà il "sub"
                .claim("email", user.getEmail())
                .claim("nome", user.getNome())
                .claim("cognome", user.getCognome())
                .claim("ruolo", user.getExtRuoloUtenteId()) // opzionale, se serve nel frontend
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000)) // 1 anno
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // ✅ Metodo per validare il token e recuperare l'ID utente
    public static String validateToken(String token) throws Exception {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // restituisce lo userId (come String)
    }

    // ✅ Metodo opzionale per recuperare un claim specifico
    public static Claims getClaims(String token) throws Exception {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}

