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
}
