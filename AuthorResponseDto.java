package com.bookapp.dto;

import com.bookapp.model.Author;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Author response data transfer object")
public class AuthorResponseDto {
    
    @Schema(description = "Author ID", example = "1")
    private Long id;
    
    @Schema(description = "Author name", example = "Machado de Assis")
    private String name;
    
    @Schema(description = "Author birth year", example = "1839")
    private Integer birthYear;
    
    @Schema(description = "Author death year", example = "1908")
    private Integer deathYear;
    
    public static AuthorResponseDto fromEntity(Author author) {
        AuthorResponseDto dto = new AuthorResponseDto();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setBirthYear(author.getBirthYear());
        dto.setDeathYear(author.getDeathYear());
        return dto;
    }
}