package com.univr.diabetes_logger.controller;

import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.model.User;
import com.univr.diabetes_logger.service.MedicService;
import com.univr.diabetes_logger.service.PatientService;
import com.univr.diabetes_logger.service.UserService;

/**
 * UserController
 */
@RestController
public class UserController {
  @Autowired
  private UserService userService;
  private PatientService patientService;
  private MedicService medicService;

  public UserController(UserService userService, PatientService patientService, MedicService medicService) {
    this.userService = userService;
    this.patientService = patientService;
    this.medicService = medicService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User user) {
    if (user.getEmail() == null || user.getEmail().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email field is required");
    }

    if (user.getPassword() == null || user.getPassword().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password field is required");
    }

    try {
      Properties props = userService.verify(user);

      if (props.containsKey("verification")) {
        return ResponseEntity.accepted().body("User is not verified yet. Please wait for verification.");
      } else {
        return ResponseEntity.ok(props);
      }

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect username or password");
    }
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User user, UriComponentsBuilder uriBuilder) {

    Iterable<User> users = userService.getAll();
    for (User u : users) {
      if (u.getEmail().equals(user.getEmail())) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already registered");
      }
    }

    if (user.getEmail() == null || user.getEmail().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email field is required");
    }
    if (user.getPassword() == null || user.getPassword().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password field is required");
    }

    if (user.getPatient() != null && user.getMedic() == null) {
      if (user.getPatient().getFirstName() == null || user.getPatient().getFirstName().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient name field is required");
      }
      if (user.getPatient().getLastName() == null || user.getPatient().getLastName().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient last name field is required");
      }
      if (user.getPatient().getBirthDate() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient birth date field is required");
      }

      Patient patient = patientService.create(user.getPatient());
      patient.setUser(user);
      user.setPatient(patient);
    } else if (user.getMedic() != null && user.getPatient() == null) {
      if (user.getMedic().getFirstName() == null || user.getMedic().getFirstName().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medic name field is required");
      }
      if (user.getMedic().getLastName() == null || user.getMedic().getLastName().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medic last name field is required");
      }

      Medic medic = medicService.create(user.getMedic());
      medic.setUser(user);
      user.setMedic(medic);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Either patient or medic must be provided");
    }

    user.setVerified(false);
    User created = userService.create(user);

    var uri = uriBuilder.path("/users/{id}").buildAndExpand(created.getId()).toUri();
    return ResponseEntity.created(uri).body(created);
  }

  @GetMapping("/users")
  public Iterable<User> getAllUsers() {
    return userService.getAll();
  }

  @GetMapping("/users/pending")
  public Iterable<User> getPendingUsers() {
    return userService.getPendingUsers();
  }

  @PatchMapping("/users/pending/{userId}")
  public User verifyPendingUser(@PathVariable Integer userId) {
    return userService.verifyPendingUser(userId);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Integer id) {
    Optional<User> user = userService.getById(id);

    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(user.get());
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user) {
    if (user.getEmail() == null || user.getEmail().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email field is required");
    }
    if (user.getRole() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role field is required");
    }
    if (user.getRole() != User.Role.ADMIN && user.getRole() != User.Role.MEDIC && user.getRole() != User.Role.PATIENT) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role");
    }
    if (user.getRole() == User.Role.PATIENT) {
      if (user.getPatient() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All patient fields are required for PATIENT role");
      }
      if (user.getPatient().getFirstName() == null || user.getPatient().getFirstName().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient name field is required");
      }
      if (user.getPatient().getLastName() == null || user.getPatient().getLastName().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient last name field is required");
      }
      if (user.getPatient().getBirthDate() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient birth date field is required");
      }
    } else if (user.getRole() == User.Role.MEDIC) {
      if (user.getMedic() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All medic fields are required for MEDIC role");
      }
      if (user.getMedic().getFirstName() == null || user.getMedic().getFirstName().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medic name field is required");
      }
      if (user.getMedic().getLastName() == null || user.getMedic().getLastName().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medic last name field is required");
      }
    }

    try {
      return ResponseEntity.ok(userService.update(id, user));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PatchMapping("/users/{id}")
  public ResponseEntity<?> patchUser(@PathVariable Integer id, @RequestBody User user) {
    if (user.getEmail() == null || user.getEmail().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email field is required");
    }
    if (user.getRole() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role field is required");
    }
    if (user.getRole() != User.Role.ADMIN && user.getRole() != User.Role.MEDIC && user.getRole() != User.Role.PATIENT) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role");
    }
    if (user.getRole() == User.Role.PATIENT) {
      if (user.getPatient() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All patient fields are required for PATIENT role");
      }
      if (user.getPatient().getFirstName() == null || user.getPatient().getFirstName().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient name field is required");
      }
      if (user.getPatient().getLastName() == null || user.getPatient().getLastName().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient last name field is required");
      }
      if (user.getPatient().getBirthDate() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient birth date field is required");
      }
    } else if (user.getRole() == User.Role.MEDIC) {
      if (user.getMedic() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All medic fields are required for MEDIC role");
      }
      if (user.getMedic().getFirstName() == null || user.getMedic().getFirstName().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medic name field is required");
      }
      if (user.getMedic().getLastName() == null || user.getMedic().getLastName().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medic last name field is required");
      }
    }

    try {
      return ResponseEntity.ok(userService.patch(id, user));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

  }

  @DeleteMapping("/users/{id}")
  public User deleteUser(@PathVariable Integer id) {
    return userService.delete(id);
  }
}
