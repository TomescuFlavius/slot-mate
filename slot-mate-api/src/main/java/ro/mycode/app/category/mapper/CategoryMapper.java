package ro.mycode.app.category.mapper;

import ro.mycode.app.category.dtos.CategoryCreateRequest;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.model.Category;

import java.util.List;

public class CategoryMapper {

    public static Category toEntity(CategoryCreateRequest categoryCreateRequest) {
        if (categoryCreateRequest == null) return null;
        return Category.builder()
                .name(categoryCreateRequest.name())
                .build();
    }

    public static CategoryResponse toDto(Category category) {
        if (category == null) return null;
        return new CategoryResponse(category.getId() , category.getName());
    }

    public static List<CategoryResponse> toDtoList(List<Category> categories) {
        return categories.stream().map(CategoryMapper::toDto).toList();
    }
}
