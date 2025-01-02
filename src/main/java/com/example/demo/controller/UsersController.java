package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.utils.GlobalResponseHandler;

import com.example.demo.model.Users;
import com.example.demo.service.ExcelService;
import com.example.demo.service.UsersService;
import com.example.demo.service.UsersUploadService;

import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private final UsersService usersService;
    private final UsersUploadService usersUploadService;
    @Autowired
    private final ExcelService excelService;

    public UsersController(UsersService usersService, UsersUploadService usersUploadService,
            ExcelService excelService) {
        this.usersService = usersService;
        this.usersUploadService = usersUploadService;
        this.excelService = excelService;
    }

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

    @PostMapping("/upload")
    public List<Users> uploadUsers(@RequestParam("file") MultipartFile file) throws Exception {
        return usersUploadService.uploadUsers(file);
    }

    /**
     * @return
     */
    @GetMapping("/getexcel")
    public ResponseEntity<String> getUsersUploadExcel() {
        try {
            List<String> headers = List.of("Username", "Mobile Number", "Email", "Password", "Roles");
            String filename = "users_" + UUID.randomUUID() + ".xlsx";
            Path filePath = Paths.get("src/main/resources/static/uploads", filename);
            Files.createDirectories(filePath.getParent());
            excelService.generateExcel(headers, filePath.toString());
            String fileUrl = "http://localhost:8080/uploads/" + filename;
            return ResponseEntity.ok(fileUrl); // Return the file URL as a string
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error generating Excel file: " + e.getMessage());
        }
    }

    // @GetMapping("/getexcel")
    // public String getUsersDetailsExcel(@RequestParam String filePath) {
    // try {
    // // Fetch all users from the database
    // List<Users> users = usersRepository.findAll();

    // // Prepare the headers for the Excel file
    // List<String> headers = List.of("ID", "Username", "Mobile Number", "Email");

    // // Prepare the data for each user
    // List<List<Object>> data = new ArrayList<>();
    // for (Users user : users) {
    // List<Object> userData = new ArrayList<>();
    // userData.add(user.getId());
    // userData.add(user.getUsername());
    // userData.add(user.getMobilenumber());
    // userData.add(user.getEmail());
    // data.add(userData);
    // }

    // // Call the ExcelGenerationService to generate the Excel file
    // excelGenerationService.generateExcel(headers, data, filePath);

    // return "Users Excel file generated successfully at: " + filePath;
    // } catch (IOException e) {
    // e.printStackTrace();
    // return "Error generating Excel file: " + e.getMessage();
    // }
    // }

    @DeleteMapping("deleteuser/{id}")
    public void deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
    }
}
