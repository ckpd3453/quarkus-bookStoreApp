package org.raku.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

    @NotBlank(message = "Book title is required")
    @Size(min = 2, max = 200,
            message = "Title must be between 2 and 200 characters")
    private String title;

    @NotBlank(message = "Author name is required")
    @Size(min = 2, max = 100,
            message = "Author name must be between 2 and 100 characters")
    private String author;

    @NotBlank(message = "Category is required")
    @Size(min = 2, max = 50,
            message = "Category must be between 2 and 50 characters")
    private String category;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01",
            message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Stock is required")
    @Min(value = 0,
            message = "Stock cannot be negative")
    private Integer stock;

    @Size(max = 1000,
            message = "Description cannot exceed 1000 characters")
    private String description;
}