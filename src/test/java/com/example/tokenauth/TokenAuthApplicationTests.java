package com.example.tokenauth;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.tokenauth.security.AuthController;
import com.example.tokenauth.security.AuthResponse;
import com.example.tokenauth.security.SecurityConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ContextConfiguration(classes = {SecurityConfiguration.class})
@WebMvcTest(controllers = AuthController.class)
class TokenAuthApplicationTests {

  private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSIehuiefaoHDUIMTYxNTgxNzYyMH0.wGyt08Cm-3urFtvtHCm3YmF7-riIl4QETw39w7Y9Vgc";

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void authenticate_withValidUser_returnsValidAuthorizationToken() throws Exception {
    ResultActions response = mvc.perform(post("/api/auth/authenticate")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"username\": \"MarkA\"}"));

    response.andExpect(status().isOk());
    response.andExpect(jsonPath("$.authorization").exists());

    AuthResponse authResponse = objectMapper.readValue(response.andReturn().getResponse().getContentAsString().getBytes(), AuthResponse.class);
    assertNotNull(authResponse);

    mvc.perform(get("/api/auth/check").header("Authorization", authResponse.getAuthorization()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.Session").value("Valid"));
  }

  @Test
  void check_withInvalidToken_returns403AccessDenied() throws Exception {
    mvc.perform(get("/api/auth/check").header("Authorization", INVALID_TOKEN))
        .andExpect(status().is(403));
  }

  @Test
  void check_withNoToken_returns403AccessDenied() throws Exception {
    mvc.perform(get("/api/auth/check"))
        .andExpect(status().is(403));
  }
}
