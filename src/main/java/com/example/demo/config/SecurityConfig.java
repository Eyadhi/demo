package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for simplicity (use with caution)
                .authorizeRequests()
                .antMatchers("/login").permitAll() // Allow login endpoint to be accessed without authentication
                .anyRequest().authenticated(); // All other endpoints require authentication
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // You can configure your authentication mechanism here (e.g., in-memory, JDBC,
        // etc.)
        // Example: In-memory authentication
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}password") // {noop} is used to indicate no encryption for simplicity
                .roles("USER");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
