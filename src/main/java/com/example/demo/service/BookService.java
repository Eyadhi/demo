package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.model.BookDetails;
import com.example.demo.repository.BookDetailsRepository;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookDetailsRepository bookDetailsRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public Book saveBook(Book book) {
        // Save book
        Book savedBook = bookRepository.save(book);

        // Create book number (UUID or sequential logic)
        for (int i = 0; i < savedBook.getNoofbooks(); i++) {
            String bookNumber = "BK-" + UUID.randomUUID().toString().substring(0, 6);
            // Save book details
            BookDetails bookDetails = new BookDetails(savedBook, bookNumber);
            bookDetails.setAvailable(true);
            bookDetailsRepository.save(bookDetails);
        }

        return savedBook;
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}