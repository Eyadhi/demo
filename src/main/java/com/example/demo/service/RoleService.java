package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role saveRole(Role role) {
        Optional<Role> existingRole = roleRepository.findByName(role.getName());

        if (existingRole.isPresent()) {
            throw new RuntimeException("Role already exists: " + role.getName());
        }

        // Directly save the new role if it doesn't exist
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional getRoleById(Long id) {
        return roleRepository.findById(id);
    }
}
