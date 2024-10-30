package com.ScalableDynamics.Jobs_Microservice.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class CorsConfig {
  private final Environment environment;

  public CorsConfig(Environment environment) {
    this.environment = environment;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())  // Disables CSRF globally
            // Permit all requests temporarily for debugging
            .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());

    return http.build();
  }



}
