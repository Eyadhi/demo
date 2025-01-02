package com.example.demo.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "your_secret_key"; // Use a more secure key

    // Generate token from username, id, and role_id
    public String generateToken(String username, Long userId, Long roleId) {
        return Jwts.builder()
                .setSubject(username) // Username will be the subject
                .claim("userId", userId) // Add userId as a custom claim
                .claim("roleId", roleId) // Add roleId as a custom claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Get username from token
    public static String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Get userId from token
    public static Long getUserIdFromToken(String token) {
        return (Long) Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("userId");
    }

    // Get roleId from token
    public static Long getRoleIdFromToken(String token) {
        return (Long) Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("roleId");
    }

    // Validate token
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
