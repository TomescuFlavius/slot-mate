package ro.mycode.app.category.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.dtos.CategoryResponseList;

import ro.mycode.app.category.exceptions.CategoryNotFoundException;
import ro.mycode.app.category.model.Category;
import ro.mycode.app.category.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CategoryQueryServiceTest {
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryQueryServiceImpl categoryQueryService;

    @Test
    void getCategoriesReturnsSavedCategories() {
        Category a = new Category();
        a.setName("a");
        Category b = new Category();
        b.setName("b");
        Category c = new Category();
        c.setName("c");
        List<Category> categories = new ArrayList<>();
        categories.add(a);
        categories.add(b);
        categories.add(c);

        when(categoryRepository.findAll()).thenReturn(categories);

        CategoryResponseList actual=categoryQueryService.getAllCategories();
        CategoryResponseList expected= new CategoryResponseList
                (categories.stream().sorted(Comparator.comparing(Category::getName))
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .toList()
        );
        assertEquals(expected, actual);
    }

    @Test
    void getCategoriesReturnEmptyList() {
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(CategoryNotFoundException.class, () -> categoryQueryService.getAllCategories());
    }

    @Test
    void getCategoriesReturnsSortedAlphabetically() {
        Category a = new Category(UUID.randomUUID(),"Z");
        Category b = new Category(UUID.randomUUID(),"B");
        Category c = new Category(UUID.randomUUID(),"V");
        List<Category> categories = new ArrayList<>(List.of(a,b,c));
        categories.sort(Comparator.comparing(Category::getName));
        when(categoryRepository.findAll()).thenReturn(categories);
        CategoryResponseList actual=categoryQueryService.getAllCategories();
        List<String> expected=List.of("B","V","Z");
        List<String> actualNames=actual.categoryResponseList().stream().map(CategoryResponse::name).toList();
        assertEquals(expected, actualNames);

    }
}
