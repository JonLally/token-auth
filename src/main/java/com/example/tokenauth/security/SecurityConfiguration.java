package com.example.tokenauth.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Import({ JwtAuthenticationProvider.class, JwtAuthenticationFilter.class, JwtTokenService.class, AuthController.class })
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final JwtAuthenticationProvider jwtAuthenticationProvider;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public SecurityConfiguration(final JwtAuthenticationProvider jwtAuthenticationProvider,
                               final JwtAuthenticationFilter jwtAuthenticationFilter) {
    this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(jwtAuthenticationProvider);
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.httpBasic().disable()
        .csrf().disable()
        .cors()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/api/auth/authenticate").permitAll()
        .anyRequest().authenticated();
  }
}
