package ro.mycode.app.category.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.app.category.dtos.CategoryCreateRequest;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.dtos.CategoryUpdateRequest;
import ro.mycode.app.category.exceptions.CategoryAlreadyExistsException;
import ro.mycode.app.category.exceptions.CategoryNotFoundException;
import ro.mycode.app.category.model.Category;
import ro.mycode.app.category.repository.CategoryRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CategoryCommandServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryCommandServiceImpl categoryCommandService;

    private final CategoryCreateRequest request = new CategoryCreateRequest("Barber");

    @Test
    void createCategoryReturnsTheSavedCategory() {
        // Arrange
        Category saved = new Category(UUID.randomUUID(), "Barber");
        when(categoryRepository.save(any())).thenReturn(saved);
        // Act
        CategoryResponse response = categoryCommandService.createCategory(request);
        // Assert
        assertThat(response).isEqualTo(new CategoryResponse(saved.getId(), saved.getName()));
    }

    @Test
    void createCategoryDoesNotSaveWhenTheNameExists() {
        // Arrange
        when(categoryRepository.existsByName("Barber")).thenReturn(true);
        // Act + Assert
        assertThatThrownBy(() -> categoryCommandService.createCategory(request))
                .isInstanceOf(CategoryAlreadyExistsException.class);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void updateCategoryRenamesTheCategory() throws CategoryNotFoundException {
        // Arrange
        Category category = new Category(UUID.randomUUID(), "Barber");
        when(categoryRepository.findCategoryByName("Barber")).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        CategoryResponse actual = categoryCommandService
                .updateCategory("Barber", new CategoryUpdateRequest("Frizerie"));

        // Assert
        assertThat(actual).isEqualTo(new CategoryResponse(category.getId(), "Frizerie"));
    }

    @Test
    void updateCategoryThrowsWhenTheCategoryDoesNotExist() {
        when(categoryRepository.findCategoryByName("Barber")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryCommandService
                .updateCategory("Barber", new CategoryUpdateRequest("Frizerie")))
                .isInstanceOf(CategoryNotFoundException.class);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void deleteCategoryReturnsTheSavedCategory() throws CategoryNotFoundException {
    String name = "Barber";
    Category category = new Category(UUID.randomUUID(), "Barber");
    when(categoryRepository.findCategoryByName(name)).thenReturn(Optional.of(category));
    CategoryResponse expected = new CategoryResponse(category.getId(), category.getName());
    CategoryResponse actual= categoryCommandService.deleteCategory(name);
    assertEquals(expected,actual);
    }
}
