package com.example.tokenauth.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final JwtTokenService jwtTokenService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    final String token = (String) authentication.getCredentials();
    if (token == null || token.isEmpty()) {
      throw new PreAuthenticatedCredentialsNotFoundException("Credentials not present in authentication");
    }
    try {
    final Claims claims = jwtTokenService.getClaims(token);
    return new JwtAuthenticationToken("user", claims);
    } catch (final JwtException e) {
      throw new BadCredentialsException("Credentials provided in authentication are not valid");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.isAssignableFrom(JwtAuthenticationToken.class);
  }

}
