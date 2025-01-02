package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Role;
import com.example.demo.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getrole")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/getrole/{id}")
    public Optional getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @PostMapping("/addrole")
    public Role saveRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

}