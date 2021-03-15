package com.example.tokenauth.security;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(onConstructor=@__(@JsonCreator))
public class AuthRequest {

  private final String username;
  private final String password;

}
