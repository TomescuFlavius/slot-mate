package ro.mycode.app.category.service;

import org.springframework.stereotype.Service;
import ro.mycode.app.category.dtos.CategoryResponseList;
import ro.mycode.app.category.exceptions.CategoryNotFoundException;
import ro.mycode.app.category.mapper.CategoryMapper;
import ro.mycode.app.category.model.Category;
import ro.mycode.app.category.repository.CategoryRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {
    private final CategoryRepository categoryRepository;

    public CategoryQueryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseList getAllCategories() throws CategoryNotFoundException {
        List<Category> categories=categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new CategoryNotFoundException();
        }
        categories.sort(Comparator.comparing(Category::getName));
        return new CategoryResponseList(CategoryMapper.toDtoList(categories));
    }

}
