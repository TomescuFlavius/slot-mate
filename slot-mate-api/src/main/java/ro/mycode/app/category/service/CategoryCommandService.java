package ro.mycode.app.category.service;

import ro.mycode.app.category.dtos.CategoryCreateRequest;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.dtos.CategoryUpdateRequest;
import ro.mycode.app.category.exceptions.CategoryNotFoundException;

public interface CategoryCommandService {
    CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest);
    CategoryResponse updateCategory(String name, CategoryUpdateRequest categoryUpdateRequest) throws CategoryNotFoundException;
    CategoryResponse deleteCategory(String name)throws CategoryNotFoundException;
}
