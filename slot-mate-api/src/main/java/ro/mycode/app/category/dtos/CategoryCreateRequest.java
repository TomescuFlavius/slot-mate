package ro.mycode.app.category.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateRequest(
        @NotBlank
        String name
)
{}
