package ro.mycode.app.category.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 50, message = "Name must be at most 50 characters")
        String name
)
{}
