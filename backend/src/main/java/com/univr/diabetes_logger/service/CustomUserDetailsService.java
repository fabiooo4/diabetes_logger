package com.univr.diabetes_logger.service;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.univr.diabetes_logger.model.CustomUserDetails;
import com.univr.diabetes_logger.model.User;
import com.univr.diabetes_logger.repository.UserRepository;

/**
 * CustomUserDetailsService
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public CustomUserDetails loadUserByUsername(String email) throws NoSuchElementException {
    User user = userRepository.findByEmail(email).orElseThrow();

    return new CustomUserDetails(user);
  }

}
