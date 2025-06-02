package com.bookapp.service;

import com.bookapp.exception.BookNotFoundException;
import com.bookapp.model.Author;
import com.bookapp.model.Book;
import com.bookapp.repository.AuthorRepository;
import com.bookapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceEnhanced {
    
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GutendexService gutendexService;
    
    @Autowired
    public BookServiceEnhanced(BookRepository bookRepository, AuthorRepository authorRepository, GutendexService gutendexService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.gutendexService = gutendexService;
    }
    
    @Transactional
    public Book saveBookFromGutendex(String title) {
        List<Book> booksFromApi = gutendexService.searchBooksByTitle(title);
        
        if (booksFromApi.isEmpty()) {
            throw new BookNotFoundException("No book found with title: " + title);
        }
        
        Book bookFromApi = booksFromApi.get(0);
        Optional<Book> existingBook = bookRepository.findByTitle(bookFromApi.getTitle());
        
        if (existingBook.isPresent()) {
            return existingBook.get();
        }
        
        Book newBook = new Book(bookFromApi.getTitle(), bookFromApi.getLanguage(), bookFromApi.getDownloadCount());
        Set<Author> authors = new HashSet<>();
        
        for (Author authorFromApi : bookFromApi.getAuthors()) {
            Optional<Author> existingAuthor = authorRepository.findByName(authorFromApi.getName());
            
            Author author = existingAuthor.orElseGet(() -> 
                new Author(authorFromApi.getName(), authorFromApi.getBirthYear(), authorFromApi.getDeathYear())
            );
            
            authors.add(author);
        }
        
        for (Author author : authors) {
            newBook.addAuthor(author);
        }
        
        return bookRepository.save(newBook);
    }
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public List<Book> getBooksByLanguage(String language) {
        return bookRepository.findByLanguage(language);
    }
    
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
    
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }
        bookRepository.deleteById(id);
    }
    
    public long getTotalBooksCount() {
        return bookRepository.count();
    }
}