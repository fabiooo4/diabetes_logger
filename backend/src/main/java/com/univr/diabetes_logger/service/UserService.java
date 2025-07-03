package com.univr.diabetes_logger.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.univr.diabetes_logger.model.User;
import com.univr.diabetes_logger.repository.UserRepository;

/**
 * UserServiceImpl
 */
@Service
public class UserService implements CrudService<User> {
  private UserRepository repository;

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private JwtService jwtService;

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public Iterable<User> getAll() {
    return repository.findAll();
  }

  @Override
  public Optional<User> getById(Integer id) {
    return repository.findById(id);
  }

  @Override
  public User create(User user) {
    System.out.println("Creating user: " + user);

    // Crypt password before saving
    user.setPassword(encoder.encode(user.getPassword()));

    return repository.save(user);
  }

  @Override
  public User update(Integer id, User user) {
    User existingUser = this.getById(id).orElseThrow();

    existingUser.setEmail(user.getEmail());
    existingUser.setPassword(encoder.encode(user.getPassword()));
    existingUser.setRole(user.getRole());

    return repository.save(existingUser);
  }

  @Override
  public User delete(Integer id) {
    User deleted = this.getById(id).orElseThrow();

    repository.deleteById(id);

    return deleted;
  }

  public Properties verify(User user) throws NoSuchElementException {

    Authentication authentication = authManager
        .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

    if (authentication.isAuthenticated()) {
      User logged_user = repository.findByEmail(user.getEmail()).orElseThrow();

      Properties props = new Properties();

      // Return user details and JWT token
      props.put("userId", logged_user.getId());
      props.put("token", jwtService.generateToken(logged_user.getEmail(), logged_user.getRole()));

      return props;
    }

    throw new RuntimeException("Invalid credentials");
  }
}
