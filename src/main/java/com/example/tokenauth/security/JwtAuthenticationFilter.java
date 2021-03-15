package com.example.tokenauth.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenService jwtTokenService;

  @Override
  protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
      final FilterChain filterChain) throws IOException, ServletException {
    log.debug("Filtering request for URI {}", request.getRequestURI());
    final Optional<String> authorizationHeader = Optional.ofNullable(request.getHeader("Authorization"));
    try {
      if (authorizationHeader.isPresent()) {
        log.info("Token found in headers, authenticating request");
        final String jwtToken = authorizationHeader.get();
        final String username = jwtTokenService.getClaims(jwtToken).getSubject();
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwtToken, username));

      } else {
        log.info("No token provided with request");
      }

      log.debug("Pre filter checks complete, continuing filter chain");
    } catch (final JwtException | IllegalArgumentException e) {
      log.error("Unexpected error parsing token", e.getMessage());
      if (log.isDebugEnabled()) {
        log.error("Debug enabled, logging stack trace for parse token error", e);
      }
    } finally {
      filterChain.doFilter(request, response);
      log.debug("Executing post filter cleanup after response is sent");
      SecurityContextHolder.clearContext();
    }
  }
}
