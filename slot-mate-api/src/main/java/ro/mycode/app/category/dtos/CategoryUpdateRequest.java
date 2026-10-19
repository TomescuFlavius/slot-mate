package ro.mycode.app.category.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryUpdateRequest(
        @NotBlank
        String name
) {
}
