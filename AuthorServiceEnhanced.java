package com.bookapp.service;

import com.bookapp.model.Author;
import com.bookapp.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceEnhanced {
    
    private final AuthorRepository authorRepository;
    
    @Autowired
    public AuthorServiceEnhanced(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    
    public List<Author> getAuthorsByYear(Integer year) {
        return authorRepository.findAuthorsByYear(year);
    }
    
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }
    
    public long getTotalAuthorsCount() {
        return authorRepository.count();
    }
}