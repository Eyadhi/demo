package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

@Entity
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookNumber;
    private Long userId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;
    private boolean isReturned = false;

    public Borrow() {
    }

    public Borrow(String bookNumber, Long userId, Date borrowDate) {
        this.bookNumber = bookNumber;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.isReturned = false;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean isReturned) {
        this.isReturned = isReturned;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

}
