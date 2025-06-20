package com.univr.diabetes_logger.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.univr.diabetes_logger.model.User;
import com.univr.diabetes_logger.repository.UserRepository;

/**
 * UserServiceImpl
 */
@Service
public class UserService implements CrudService<User> {
  private UserRepository repository;

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
    return repository.save(user);
  }

  @Override
  public User update(Integer id, User user) {
    User existingUser = this.getById(id).orElseThrow();

    existingUser.setEmail(user.getEmail());
    existingUser.setPassword(user.getPassword());

    return repository.save(existingUser);
  }

  @Override
  public User delete(Integer id) {
    User deleted = this.getById(id).orElseThrow();

    repository.deleteById(id);

    return deleted;
  }
}
