package ro.mycode.app.category.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ro.mycode.app.category.dtos.CategoryCreateRequest;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.exceptions.CategoryAlreadyExistsException;
import ro.mycode.app.category.mapper.CategoryMapper;
import ro.mycode.app.category.model.Category;
import ro.mycode.app.category.repository.CategoryRepository;

@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private final CategoryRepository categoryRepository;
     public CategoryCommandServiceImpl(CategoryRepository categoryRepository) {
         this.categoryRepository = categoryRepository;
     }


    @Transactional
    @Override
    public CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest) {
        if (categoryRepository.existsByName(categoryCreateRequest.name())) throw new CategoryAlreadyExistsException();
        Category category = CategoryMapper.toEntity(categoryCreateRequest);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toDto(savedCategory);
    }
}
