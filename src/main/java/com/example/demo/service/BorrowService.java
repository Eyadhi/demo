package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Borrow;
import com.example.demo.repository.BorrowRepository;

@Service
public class BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    public BorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }

    public Optional<Borrow> getBorrowById(Long id) {
        return borrowRepository.findById(id);
    }

    public Borrow borrowBook(Borrow borrow) {
        return borrowRepository.save(borrow);
    }

    public Borrow returnBook(Long borrowId) throws Exception {
        Optional<Borrow> optionalBorrow = borrowRepository.findById(borrowId);
        if (optionalBorrow.isPresent()) {
            Borrow borrow = optionalBorrow.get();
            borrow.setReturned(true);
            // borrow.setReturnDate(new Date());
            return borrowRepository.save(borrow);
        } else {
            throw new Exception("Borrow record not found.");
        }
    }

}
