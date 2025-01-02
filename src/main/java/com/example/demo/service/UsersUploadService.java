package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.Users;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UsersRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersUploadService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    private static final String TEMPLATE_FOLDER_PATH = "./src/main/resources/template";

    public UsersUploadService(UsersRepository usersRepository, RoleRepository roleRepository) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
    }

    public List<Users> uploadUsers(MultipartFile file) throws Exception {
        List<Users> users = new ArrayList<>();
        // File templateFolder = new File(TEMPLATE_FOLDER_PATH);

        // if (!templateFolder.exists()) {
        // templateFolder.mkdirs(); // Create the folder if it doesn't exist (Not
        // recommended in production)
        // }

        // String fileName = file.getOriginalFilename();
        // File uploadedFile = new File(templateFolder, fileName);

        // try {
        // file.transferTo(uploadedFile); // Save the file to the templates folder
        // } catch (IOException e) {
        // throw new RuntimeException("Error saving the uploaded file to the templates
        // folder: " + e.getMessage());
        // }

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0)
                    continue; // Skip header row

                Users user = new Users();
                user.setUsername(getCellValue(row.getCell(0))); // username
                user.setMobilenumber(getCellValue(row.getCell(1))); // mobilenumber
                user.setEmail(getCellValue(row.getCell(2))); // email
                user.setPassword(getCellValue(row.getCell(3))); // password

                // Handle the roleIds cell
                Cell roleCell = row.getCell(4);
                List<Long> roleIds = new ArrayList<>();

                if (roleCell != null) {
                    if (roleCell.getCellType() == CellType.NUMERIC) {
                        roleIds.add((long) roleCell.getNumericCellValue());
                    } else if (roleCell.getCellType() == CellType.STRING) {
                        String roleIdsString = roleCell.getStringCellValue();
                        String[] roleIdsArray = roleIdsString.split(",");
                        for (String roleIdStr : roleIdsArray) {
                            try {
                                roleIds.add(Long.parseLong(roleIdStr.trim()));
                            } catch (NumberFormatException e) {
                                throw new RuntimeException("Invalid role ID format: " + roleIdStr);
                            }
                        }
                    } else {
                        throw new RuntimeException("Invalid role ID format in row " + row.getRowNum());
                    }
                }
                user.setRoleIds(roleIds);

                // Fetch Roles from the database
                List<Role> roles = roleRepository.findAllById(roleIds);
                if (roles.isEmpty()) {
                    throw new RuntimeException("Invalid role IDs provided in row " + row.getRowNum());
                }

                // Set roles to the user
                user.setRoles(roles);
                users.add(user);
            }
            workbook.close();
            // Save all users to the database
            return usersRepository.saveAll(users);
        } catch (Exception e) {
            throw new Exception("Error uploading file: " + e.getMessage());
        }
    }

    // Helper method to get cell value as String (for different data types)
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

}
