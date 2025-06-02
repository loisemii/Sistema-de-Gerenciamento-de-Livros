package com.bookapp.dto;

import com.bookapp.model.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Book response data transfer object")
public class BookResponseDto {
    
    @Schema(description = "Book ID", example = "1")
    private Long id;
    
    @Schema(description = "Book title", example = "Dom Casmurro")
    private String title;
    
    @Schema(description = "Book language code", example = "pt")
    private String language;
    
    @Schema(description = "Number of downloads", example = "1500")
    private Integer downloadCount;
    
    @Schema(description = "List of authors")
    private Set<AuthorResponseDto> authors;
    
    public static BookResponseDto fromEntity(Book book) {
        BookResponseDto dto = new BookResponseDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setLanguage(book.getLanguage());
        dto.setDownloadCount(book.getDownloadCount());
        dto.setAuthors(book.getAuthors().stream()
                .map(AuthorResponseDto::fromEntity)
                .collect(Collectors.toSet()));
        return dto;
    }
}