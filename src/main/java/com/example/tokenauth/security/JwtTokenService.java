package com.example.tokenauth.security;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtTokenService {
  
  @Value("${app.security.jwt.secret}")
  private String secret;
  private JwtParser jwtParser;
  private SecretKeySpec secretKey;

  public Claims getClaims(final String jwtToken) {
    log.debug("Parsing claims for token: {}", jwtToken);
    return getParser().parseClaimsJws(jwtToken).getBody();
  }

  public String createAuthorizationToken(final String username, final Map<String, Object> claims) {
    log.info("Creating authorization token for {} with {} claims", username, claims.size());
    return Jwts.builder()
        .setSubject(username)
        .signWith(getSecretKey())
        .setIssuedAt(Date.from(Instant.now()))
        .addClaims(claims)
        .compact();
  }

  private JwtParser getParser() {
    if (jwtParser == null) {
      log.debug("Initialising JwtParser for validating and parsing JWT tokens");
      jwtParser = Jwts.parserBuilder().setSigningKey(getSecretKey()).build();
    }
    return jwtParser;
  }

  private SecretKeySpec getSecretKey() {
    if (secretKey == null) {
      log.debug("Initialising SecretKey for creating and parsing JWT tokens");
      secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
    }
    return secretKey;
  }
}
