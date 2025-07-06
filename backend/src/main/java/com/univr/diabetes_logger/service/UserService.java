package com.univr.diabetes_logger.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.model.User;
import com.univr.diabetes_logger.model.User.Role;
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

  public Iterable<User> getPendingUsers() {
    return repository.findAll().stream().filter(user -> !user.isVerified()).collect(Collectors.toList());
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

    String email = user.getEmail();
    if (email != null && !email.isEmpty() && !email.equals(existingUser.getEmail())) {
      // Check if the email is already in use by another user
      if (repository.findByEmail(email).isPresent()) {
        throw new IllegalArgumentException("Email is already in use");
      }

      existingUser.setEmail(user.getEmail());
    }

    String password = user.getPassword();
    if (password != null && !password.isEmpty() && !encoder.matches(password, existingUser.getPassword())) {
      // Crypt password before saving
      existingUser.setPassword(encoder.encode(password));
    }

    Role role = user.getRole();
    if ((role != Role.ADMIN || role != Role.MEDIC || role != Role.PATIENT) && !role.equals(existingUser.getRole())) {
      existingUser.setRole(role);
    }

    Boolean verified = user.isVerified();
    if (verified != null && !verified.equals(existingUser.isVerified())) {
      existingUser.setVerified(verified);
    }

    Patient patient = user.getPatient();
    Medic medic = user.getMedic();
    if (patient != null && existingUser.getPatient() == null) {
      existingUser.updatePatient(patient);
    } else if (medic != null && existingUser.getMedic() == null) {
      existingUser.updateMedic(medic);
    }

    return repository.save(existingUser);
  }

  public User patch(Integer id, User user) {
    User existingUser = this.getById(id).orElseThrow();

    String email = user.getEmail();
    if (email != null && !email.isEmpty() && !email.equals(existingUser.getEmail())) {
      // Check if the email is already in use by another user
      if (repository.findByEmail(email).isPresent()) {
        throw new IllegalArgumentException("Email is already in use");
      }

      existingUser.setEmail(user.getEmail());
    }

    String password = user.getPassword();
    if (password != null && !password.isEmpty() && !encoder.matches(password, existingUser.getPassword())) {
      // Crypt password before saving
      existingUser.setPassword(encoder.encode(password));
    }

    Patient patient = user.getPatient();
    Medic medic = user.getMedic();
    if (patient != null && existingUser.getPatient() != null && medic == null) {
      existingUser.updatePatient(patient);
    } else if (medic != null && existingUser.getMedic() != null && patient == null) {
      existingUser.updateMedic(medic);
    } else {
      throw new IllegalArgumentException("Only one of patient or medic can be updated");
    }

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

      if (logged_user.isVerified()) {
        // Return user details and JWT token
        props.put("userId", logged_user.getId());
        props.put("token", jwtService.generateToken(logged_user.getEmail(), logged_user.getRole()));
      } else {
        // User is not verified, return a message
        props.put("", "User is not verified, wait for Admin to accept you");
      }

      return props;
    }

    throw new RuntimeException("Invalid credentials");
  }
}
