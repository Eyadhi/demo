package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.demo.model.Users;
import com.example.demo.model.Role;
import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.RoleRepository;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UsersService(UsersRepository userRepository, RoleRepository roleRepository) {
        this.usersRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    public Users createUser(String username, String mobilenumber, String email, String password,
            List<String> roleNames) {
        if (usersRepository.existsByEmail(email)) {
            throw new RuntimeException("User with this email already exists!");
        }

        // Fetch roles from DB by names
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        }
        Users user = new Users(username, mobilenumber, email, password, roles);
        return usersRepository.save(user);
    }

    public Users saveUser(Users user) {
        return usersRepository.save(user);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}
