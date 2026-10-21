package ro.mycode.app.category.dtos;

import java.util.UUID;

public record CategoryResponse(
    UUID id,
    String name
)
{}
