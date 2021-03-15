package com.example.tokenauth.security;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {
  
  private final JwtTokenService jwtTokenService;

  @PostMapping(path = "/authenticate")
  public AuthResponse authenticate(@RequestBody final AuthRequest request) {
    log.debug("Received request to authenticate for {}", request.getUsername());
    // authenticate user
    return new AuthResponse(jwtTokenService.createAuthorizationToken(request.getUsername(), Collections.emptyMap()));
  }

  @GetMapping(path = "/check")
  public Map<String, String> check() {
    return Collections.singletonMap("Session", "Valid");
  }
}
