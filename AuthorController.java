package com.bookapp.controller;

import com.bookapp.dto.ApiResponse;
import com.bookapp.dto.AuthorResponseDto;
import com.bookapp.model.Author;
import com.bookapp.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Authors", description = "Author management API")
public class AuthorController {
    
    private final AuthorService authorService;
    
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    
    @Operation(
        summary = "Get all authors",
        description = "Retrieve all authors stored in the database"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Authors retrieved successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorResponseDto>>> getAllAuthors() {
        try {
            List<Author> authors = authorService.getAllAuthors();
            List<AuthorResponseDto> authorDtos = authors.stream()
                .map(AuthorResponseDto::fromEntity)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(
                ApiResponse.success("Authors retrieved successfully", authorDtos)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving authors: " + e.getMessage()));
        }
    }
    
    @Operation(
        summary = "Get authors by year",
        description = "Retrieve all authors who were alive in a specific year"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Authors retrieved successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", 
            description = "Invalid year",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    @GetMapping("/year/{year}")
    public ResponseEntity<ApiResponse<List<AuthorResponseDto>>> getAuthorsByYear(
            @Parameter(description = "Year to search for authors", example = "1800")
            @PathVariable Integer year) {
        
        try {
            if (year < 0 || year > 2024) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Invalid year: " + year));
            }
            
            List<Author> authors = authorService.getAuthorsByYear(year);
            List<AuthorResponseDto> authorDtos = authors.stream()
                .map(AuthorResponseDto::fromEntity)
                .collect(Collectors.toList());
            
            String message = authors.isEmpty() 
                ? "No authors found who were alive in year: " + year
                : "Authors retrieved successfully for year: " + year;
            
            return ResponseEntity.ok(
                ApiResponse.success(message, authorDtos)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving authors by year: " + e.getMessage()));
        }
    }
    
    @Operation(
        summary = "Get author by ID",
        description = "Retrieve a specific author by their ID"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Author found",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", 
            description = "Author not found",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponseDto>> getAuthorById(
            @Parameter(description = "Author ID", example = "1")
            @PathVariable Long id) {
        
        try {
            Author author = authorService.getAuthorById(id);
            if (author != null) {
                AuthorResponseDto authorDto = AuthorResponseDto.fromEntity(author);
                return ResponseEntity.ok(
                    ApiResponse.success("Author found", authorDto)
                );
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Author not found with ID: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving author: " + e.getMessage()));
        }
    }
}