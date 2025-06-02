package com.bookapp.controller;

import com.bookapp.dto.ApiResponse;
import com.bookapp.dto.BookResponseDto;
import com.bookapp.dto.BookSearchRequestDto;
import com.bookapp.model.Book;
import com.bookapp.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Book management API")
public class BookController {
    
    private final BookService bookService;
    
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @Operation(
        summary = "Search and save book by title",
        description = "Search for a book by title using the Gutendex API and save it to the database"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Book found and saved successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", 
            description = "Book not found",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", 
            description = "Invalid request",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<BookResponseDto>> searchAndSaveBook(
            @Valid @RequestBody BookSearchRequestDto request) {
        
        try {
            Book book = bookService.saveBookFromGutendex(request.getTitle());
            
            if (book != null) {
                BookResponseDto bookDto = BookResponseDto.fromEntity(book);
                return ResponseEntity.ok(
                    ApiResponse.success("Book found and saved successfully", bookDto)
                );
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("No book found with title: " + request.getTitle()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error searching for book: " + e.getMessage()));
        }
    }
    
    @Operation(
        summary = "Get all registered books",
        description = "Retrieve all books stored in the database"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Books retrieved successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookResponseDto>>> getAllBooks() {
        try {
            List<Book> books = bookService.getAllBooks();
            List<BookResponseDto> bookDtos = books.stream()
                .map(BookResponseDto::fromEntity)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(
                ApiResponse.success("Books retrieved successfully", bookDtos)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving books: " + e.getMessage()));
        }
    }
    
    @Operation(
        summary = "Get books by language",
        description = "Retrieve all books in a specific language"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Books retrieved successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", 
            description = "Invalid language code",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    @GetMapping("/language/{languageCode}")
    public ResponseEntity<ApiResponse<List<BookResponseDto>>> getBooksByLanguage(
            @Parameter(description = "Language code (PT, EN, ES, FR)", example = "pt")
            @PathVariable String languageCode) {
        
        try {
            List<Book> books = bookService.getBooksByLanguage(languageCode.toLowerCase());
            List<BookResponseDto> bookDtos = books.stream()
                .map(BookResponseDto::fromEntity)
                .collect(Collectors.toList());
            
            String message = books.isEmpty() 
                ? "No books found for language: " + languageCode
                : "Books retrieved successfully for language: " + languageCode;
            
            return ResponseEntity.ok(
                ApiResponse.success(message, bookDtos)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving books by language: " + e.getMessage()));
        }
    }
    
    @Operation(
        summary = "Get book by ID",
        description = "Retrieve a specific book by its ID"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Book found",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", 
            description = "Book not found",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponseDto>> getBookById(
            @Parameter(description = "Book ID", example = "1")
            @PathVariable Long id) {
        
        try {
            Book book = bookService.getBookById(id);
            if (book != null) {
                BookResponseDto bookDto = BookResponseDto.fromEntity(book);
                return ResponseEntity.ok(
                    ApiResponse.success("Book found", bookDto)
                );
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Book not found with ID: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving book: " + e.getMessage()));
        }
    }
}