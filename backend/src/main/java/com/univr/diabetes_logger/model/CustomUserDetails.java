package com.univr.diabetes_logger.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * CustomUserDetails
 */
public class CustomUserDetails implements UserDetails {

  private User user;

  public CustomUserDetails() {
  }

  public CustomUserDetails(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    String role = "NONE";

    if (user.getRole() != null) {
      role = user.getRole().toString();
    }

    return Collections.singleton(new SimpleGrantedAuthority(role));
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  public boolean checkId(Integer id) {
    return id.equals(user.getId());
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

}
