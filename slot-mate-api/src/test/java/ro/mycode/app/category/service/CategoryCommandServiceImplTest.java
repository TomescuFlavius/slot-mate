package ro.mycode.app.category.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.app.category.dtos.CategoryCreateRequest;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.exceptions.CategoryAlreadyExistsException;
import ro.mycode.app.category.model.Category;
import ro.mycode.app.category.repository.CategoryRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Test
    void createCategorySavesAndReturnsTheSavedCategory() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(categoryRepository.existsByName("Barber")).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category(id, "Barber"));

        // Act
        CategoryResponse response = categoryCommandService.createCategory(new CategoryCreateRequest("Barber"));

        // Assert
        assertThat(response).isEqualTo(new CategoryResponse(id, "Barber"));
    }

    @Test
    void createCategoryThrowsAndDoesNotSaveWhenTheNameExists() {
        // Arrange
        when(categoryRepository.existsByName("Barber")).thenReturn(true);

        // Act + Assert
        assertThatThrownBy(() -> categoryCommandService.createCategory(new CategoryCreateRequest("Barber")))
                .isInstanceOf(CategoryAlreadyExistsException.class)
                .hasMessage("A category with this name already exists");
        verify(categoryRepository, never()).save(any());
    }
}
