package com.bookapp.controller;

import com.bookapp.dto.ApiResponse;
import com.bookapp.service.AuthorServiceEnhanced;
import com.bookapp.service.BookServiceEnhanced;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Health", description = "Application health check API")
public class HealthController {
    
    private final BookServiceEnhanced bookService;
    private final AuthorServiceEnhanced authorService;
    
    @Autowired
    public HealthController(BookServiceEnhanced bookService, AuthorServiceEnhanced authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }
    
    @Operation(
        summary = "Health check",
        description = "Check the health status of the application and database"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> healthCheck() {
        Map<String, Object> healthData = new HashMap<>();
        healthData.put("status", "UP");
        healthData.put("timestamp", LocalDateTime.now());
        healthData.put("totalBooks", bookService.getTotalBooksCount());
        healthData.put("totalAuthors", authorService.getTotalAuthorsCount());
        healthData.put("version", "1.0.0");
        
        return ResponseEntity.ok(
            ApiResponse.success("Application is healthy", healthData)
        );
    }
}