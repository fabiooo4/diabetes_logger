package com.univr.diabetes_logger.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.univr.diabetes_logger.model.CustomUserDetails;
import com.univr.diabetes_logger.model.User.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * JwtService
 */
@Service
public class JwtService {
  private String key;

  public JwtService() {
    try {
      KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
      SecretKey secret = keyGenerator.generateKey();

      key = Base64.getEncoder().encodeToString(secret.getEncoded());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  public String generateToken(String email, Role role) {
    String roleString = "NONE";

    if (role != null) {
      roleString = role.toString();
    }

    return Jwts.builder()
        .claims()
        .subject(email)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 60 * 60 * 2))
        .add("role", roleString)
        .and()
        .signWith(getKey())
        .compact();
  }

  private SecretKey getKey() {
    byte[] bytes = Decoders.BASE64.decode(key);
    return Keys.hmacShaKeyFor(bytes);
  }

  public String extractEmail(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private String extractRole(String token) {
    final Claims claims = extractAllClaims(token);

    String role = claims.get("role", String.class);

    if (role == null) {
      return "NONE";
    } else {
      return role.toString();
    }
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String email = extractEmail(token);
    final SimpleGrantedAuthority role = new SimpleGrantedAuthority(extractRole(token));

    return (email.equals(userDetails.getUsername()) && userDetails.getAuthorities().contains(role)
        && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

}
