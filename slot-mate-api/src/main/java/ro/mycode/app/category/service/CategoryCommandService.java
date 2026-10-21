package ro.mycode.app.category.service;

import ro.mycode.app.category.dtos.CategoryCreateRequest;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.exceptions.CategoryAlreadyExistsException;

public interface CategoryCommandService {
    CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest) throws CategoryAlreadyExistsException;
}
