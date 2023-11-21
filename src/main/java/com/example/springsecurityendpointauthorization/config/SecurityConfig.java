package com.example.springsecurityendpointauthorization.config;

import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.httpBasic()
            .and()
            .authorizeHttpRequests()
              .anyRequest().authenticated() /*Endpoint level authorization
               matcherMethod + authorization rule
               .anyRequest().permitAll()
               .anyRequest().denyAll()
               AUTHORITIES:
               .anyRequest().hasAuthority("read")
               .anyRequest().hasAnyAuthority("read", "write")
                ROLES: The role prefix is "ROLE_" by default
                .anyRequest().hasRole("ADMIN")
                .anyRequest().hasAnyRole("ADMIN", "USER")
               */
             .and()
            .build();
  }
  @Bean
  public UserDetailsService userDetailsService() {
    var uds = new InMemoryUserDetailsManager();

    var u = User.withUsername("user")
            .password(passwordEncoder().encode("12345"))
            .authorities("read")
            .build();
    var u2 = User.withUsername("juan")
          .password(passwordEncoder().encode("12345"))
          .roles("ADMIN") // equivalent to .authorities("ROLE_ADMIN")
          .build();

   uds.createUser(u);

   return uds;
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
}
