package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Users;
import com.example.demo.service.UsersService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrow")
public class BorrowController {
    @Autowired
    private BorrowService usersService;

    @GetMapping("/getusers")
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/getuser/{id}")
    public Optional getUserById(@PathVariable Long id) {
        return usersService.getUserById(id);
    }

    @PostMapping("/adduser")
    public Users saveUser(@RequestBody Users user) {
        return usersService.saveUser(user);
    }

    @DeleteMapping("deleteuser/{id}")
    public void deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
    }
}
