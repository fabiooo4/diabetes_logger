package com.univr.diabetes_logger.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.univr.diabetes_logger.service.CustomUserDetailsService;

/**
 * SecurityConfigurer
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Autowired
  private JwtFilter jwtFilter;

  @Bean
  // Authentication configuration
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(customizer -> customizer.disable())
        .authorizeHttpRequests(
            request -> {
              request
                  .requestMatchers("/login", "/register").permitAll();

              // Patient is allowed to
              request
                  .requestMatchers("/medics").hasAnyAuthority("PATIENT", "ADMIN");

              // Medic is allowed to
              request
                  .requestMatchers(HttpMethod.GET, "/patients", "patients/{id}").hasAnyAuthority("MEDIC", "ADMIN")
                  .requestMatchers(HttpMethod.PUT, "/patients/{id}").hasAnyAuthority("MEDIC", "ADMIN");

              // Admin is allowed to
              request.anyRequest().hasAuthority("ADMIN");
            })
        // .formLogin(Customizer.withDefaults()) // For web form
        .httpBasic(Customizer.withDefaults()) // For non web clients
        // Create a new session for every request
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

    provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
    provider.setUserDetailsService(userDetailsService);

    return provider;
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

}
