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
                  .access(new WebExpressionAuthorizationManager("hasAuthority('ADMIN') or authentication.getDetails().checkId(#id)"));
                  // Users -----------------------------------------------


              // Patient is allowed to
              // request
                  //
                  // Reports ---------------------------------------------
                  // .requestMatchers(HttpMethod.GET, "/reports/{id}")
                  // .access(new WebExpressionAuthorizationManager(
                  //     "hasAnyAuthority('PATIENT', 'ADMIN') and authentication.getDetails().checkId(#id)"))
                  // .requestMatchers(HttpMethod.POST, "/reports/{id}")
                  // .access(new WebExpressionAuthorizationManager(
                  //     "hasAnyAuthority('PATIENT', 'ADMIN') and authentication.getDetails().checkId(#id)"))
                  // .requestMatchers(HttpMethod.PUT, "/reports/{id}")
                  // .access(new WebExpressionAuthorizationManager(
                  //     "hasAnyAuthority('PATIENT', 'ADMIN') and authentication.getDetails().checkId(#id)"))
                  // Reports ---------------------------------------------
                  //
                  // Notifications ---------------------------------------
                  // .requestMatchers(HttpMethod.GET, "/reports/{id}")
                  // .access(new WebExpressionAuthorizationManager(
                  //     "hasAnyAuthority('PATIENT', 'ADMIN') and authentication.getDetails().checkId(#id)"))
                  // Notifications ---------------------------------------
                  ;

              // Medic is allowed to
              request
                  // Users -----------------------------------------------
                  .requestMatchers(HttpMethod.GET, "/users/{id}")
                  .access(new WebExpressionAuthorizationManager(
                      "hasAnyAuthority('MEDIC', 'ADMIN') and authentication.getDetails().checkId(#id)"))
                  // Users -----------------------------------------------
                  //
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
                  // .requestMatchers(HttpMethod.GET, "/reports", "/reports/{id}")
                  // .hasAnyAuthority("MEDIC", "ADMIN")
                  // Reports ---------------------------------------------
                  //
                  // Notifications ---------------------------------------
                  // .requestMatchers(HttpMethod.GET, "/notifications/{id}")
                  // .access(new WebExpressionAuthorizationManager(
                  //     "hasAnyAuthority('MEDIC', 'ADMIN') and authentication.getDetails().checkId(#id)"))
                  // Notifications ---------------------------------------
                  ;

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
