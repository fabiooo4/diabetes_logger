package com.univr.diabetes_logger.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
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
              // Everyone is allowed to
              request
                  .requestMatchers("/login", "/register").permitAll()
                  // Users -----------------------------------------------
                  .requestMatchers(HttpMethod.GET, "/users/{id}")
                  // Allow access to users/{id} endpoint only for the authenticated id
                  .access(new WebExpressionAuthorizationManager("hasAuthority('ADMIN') or authentication.getDetails().checkId(#id)"))
                  .requestMatchers(HttpMethod.PATCH, "/users/{id}")
                  // Allow access to users/{id} endpoint only for the authenticated id
                  .access(new WebExpressionAuthorizationManager("hasAuthority('ADMIN') or authentication.getDetails().checkId(#id)"));
                  // Users -----------------------------------------------
              // Patient is allowed to
              request
                  // Reports ---------------------------------------------
                  .requestMatchers(HttpMethod.GET, "/reports/user/{userId}", "/reports/user/{userId}/{id}")
                  .access(new WebExpressionAuthorizationManager(
                      "hasAnyAuthority('ADMIN', 'MEDIC') or (hasAnyAuthority('PATIENT') and authentication.getDetails().checkId(#userId))"))
                  .requestMatchers(HttpMethod.POST, "/reports/user/{userId}")
                  .access(new WebExpressionAuthorizationManager(
                      "hasAuthority('ADMIN') or (hasAnyAuthority('PATIENT') and authentication.getDetails().checkId(#userId))"))
                  .requestMatchers(HttpMethod.PUT, "/reports/user/{userId}/{id}")
                  .access(new WebExpressionAuthorizationManager(
                      "hasAuthority('ADMIN') or (hasAnyAuthority('PATIENT') and authentication.getDetails().checkId(#userId))"))
                  .requestMatchers(HttpMethod.DELETE, "/reports/user/{userId}/{id}")
                  .access(new WebExpressionAuthorizationManager(
                      "hasAuthority('ADMIN') or (hasAnyAuthority('PATIENT') and authentication.getDetails().checkId(#userId))"))
                  // Reports --------------------------------------------
                  // Notifications ---------------------------------------
                  .requestMatchers(HttpMethod.GET, "/notifications/user/{userId}/{id}", "/notifications/user/{userId}")
                  .access(new WebExpressionAuthorizationManager(
                     "hasAuthority('ADMIN') or (hasAnyAuthority('PATIENT', 'MEDIC') and authentication.getDetails().checkId(#userId))"))
                  .requestMatchers(HttpMethod.DELETE, "/notifications/user/{userId}/{id}")
                  .access(new WebExpressionAuthorizationManager(
                    "hasAuthority('ADMIN') or (hasAnyAuthority('PATIENT', 'MEDIC') and authentication.getDetails().checkId(#userId))"))
                  .requestMatchers(HttpMethod.PATCH, "/notifications/user/{userId}/{id}", "/notifications/user/{userId}")
                  .access(new WebExpressionAuthorizationManager(
                    "hasAuthority('ADMIN') or (hasAnyAuthority('PATIENT', 'MEDIC') and authentication.getDetails().checkId(#userId))"))
                  // Notifications ---------------------------------------
                  ;

              // Medic is allowed to
              request
                  // Patients --------------------------------------------
                  .requestMatchers(HttpMethod.GET, "/patients", "/patients/{id}")
                  .hasAnyAuthority("MEDIC", "ADMIN")
                  .requestMatchers(HttpMethod.PUT, "/patients/{id}")
                  .hasAnyAuthority("MEDIC", "ADMIN")
                  // Patients --------------------------------------------
                  //
                  // Therapies -------------------------------------------
                  .requestMatchers(HttpMethod.GET, "/therapies", "/therapies/{id}")
                  .hasAnyAuthority("MEDIC", "ADMIN")
                  .requestMatchers(HttpMethod.POST, "/therapies")
                  .hasAnyAuthority("MEDIC", "ADMIN")
                  .requestMatchers(HttpMethod.PUT, "/therapies/{id}")
                  .hasAnyAuthority("MEDIC", "ADMIN")
                  // Therapies -------------------------------------------
                  //
                  // Reports ---------------------------------------------
                  .requestMatchers(HttpMethod.GET, "/reports", "/reports/{id}")
                  .hasAnyAuthority("MEDIC", "ADMIN")
                  // Reports ---------------------------------------------
                  //
                  // MedicChangeLog --------------------------------------
                  .requestMatchers(HttpMethod.GET, "/medicchangelog/{id}", "/medicchangelog/medic/{medicId}",
                          "/medicchangelog/patient/{patientId}")
                  .hasAnyAuthority("MEDIC", "ADMIN")
                  .requestMatchers(HttpMethod.POST, "/medicchangelog")
                  .hasAnyAuthority("MEDIC", "ADMIN")
                  // MedicChangeLog --------------------------------------
              ;

              // Admin is allowed to
              request.anyRequest().hasAuthority("ADMIN");
            })
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
