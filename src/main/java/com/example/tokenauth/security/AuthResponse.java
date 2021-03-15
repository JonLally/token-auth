package com.example.tokenauth.security;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(onConstructor=@__(@JsonCreator))
public class AuthResponse {

  private final String authorization;

}
