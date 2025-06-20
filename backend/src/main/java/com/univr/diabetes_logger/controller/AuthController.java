package com.univr.diabetes_logger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.univr.diabetes_logger.model.LoginRequest;

/**
 * AuthController
 */
@RestController
public class AuthController {
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    // TODO: Add login
    return ResponseEntity.internalServerError().build();
  }

  @PostMapping("/register")
  public ResponseEntity<?> Register(@RequestBody LoginRequest loginRequest) {
    // TODO: Add register
    return ResponseEntity.internalServerError().build();
  }
}
