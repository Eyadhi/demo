package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Login;
import com.example.demo.model.Users;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.utils.JwtUtil;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // @Autowired
    // private MyUserDetailsService userDetailsService; // To retrieve user
    // information, including id and role

    @PostMapping("/login")
    public String login(@RequestBody Login login) throws AuthenticationException {
        try {
            // Authenticate the user using the AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login.getUsername(),
                            login.getPassword()));

            Users userEntity = usersRepository.findByUsername(login.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Long roleId = usersRepository.findRoleIdByUserId(userEntity.getId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            String token = jwtUtil.generateToken(userEntity.getUsername(), userEntity.getId(), roleId);
            return token;

        } catch (Exception e) {
            // Handle any authentication or role not found error
            return "Invalid credentials or role not found";
        }
    }
}
