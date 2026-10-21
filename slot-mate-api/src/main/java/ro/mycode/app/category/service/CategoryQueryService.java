package ro.mycode.app.category.service;

import ro.mycode.app.category.dtos.CategoryResponseList;
import ro.mycode.app.category.exceptions.CategoryNotFoundException;

public interface CategoryQueryService {

    CategoryResponseList getAllCategories() throws CategoryNotFoundException;
}
