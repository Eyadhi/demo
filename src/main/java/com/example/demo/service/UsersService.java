package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            List<Long> roleIds) {
        if (usersRepository.existsByEmail(email)) {
            throw new RuntimeException("User with this email already exists!");
        }

        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) { // Use roleIds correctly
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found for ID: " + roleId));
            roles.add(role);
        }

        Users user = new Users(username, mobilenumber, email, password, new ArrayList<>(roleIds));
        user.setRoles(roles); // Assign the roles to the user
        return usersRepository.save(user);
    }

    public Users saveUser(Users user) {
        return usersRepository.save(user);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}
