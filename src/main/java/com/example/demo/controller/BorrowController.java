package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Borrow;
import com.example.demo.service.BorrowService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrow")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @GetMapping("/getborrows")
    public List<Borrow> getAllBorrows() {
        return borrowService.getAllBorrows();
    }

    @GetMapping("/getborrow/{id}")
    public Optional getBorrowById(@PathVariable Long id) {
        return borrowService.getBorrowById(id);
    }

    @PostMapping("/addborrow")
    public Borrow borrowBook(@RequestBody Borrow borrow) {
        return borrowService.borrowBook(borrow);
    }

}
