package ro.mycode.app.category.service;

import org.springframework.data.repository.query.Param;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.dtos.CategoryResponseList;
import ro.mycode.app.category.exceptions.CategoryNotFoundException;

public interface CategoryQueryService {

    CategoryResponseList getAllCategories() throws CategoryNotFoundException;
    CategoryResponseList getAllCategoriesByName(@Param("name") String name) throws CategoryNotFoundException;
}
