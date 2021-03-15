package com.example.tokenauth.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

  private static final long serialVersionUID = 4511020795233339113L;
  private transient Object principal;
  private transient Object credential;

  public JwtAuthenticationToken(final Object principal, final Object credential,
      final Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.credential = credential;
    super.setAuthenticated(true);
  }

  public JwtAuthenticationToken(final Object principal, final Object credential) {
    super(null);
    this.principal = principal;
    this.credential = credential;
    super.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return credential;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }
}
