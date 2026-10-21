package ro.mycode.app.category.service;

import org.springframework.stereotype.Service;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.dtos.CategoryResponseList;
import ro.mycode.app.category.exceptions.CategoryNotFoundException;
import ro.mycode.app.category.mapper.CategoryMapper;
import ro.mycode.app.category.repository.CategoryRepository;
@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {
    private final CategoryRepository categoryRepository;

    public CategoryQueryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseList getAllCategories() throws CategoryNotFoundException {
        if(categoryRepository.findAll().isEmpty()) throw new  CategoryNotFoundException();
        return new CategoryResponseList(CategoryMapper.toDtoList(categoryRepository.findAll()));
    }

}
