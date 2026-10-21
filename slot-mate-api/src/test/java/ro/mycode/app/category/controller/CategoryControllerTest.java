package ro.mycode.app.category.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ro.mycode.app.category.dtos.CategoryCreateRequest;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.exceptions.CategoryAlreadyExistsException;
import ro.mycode.app.category.service.CategoryCommandService;
import ro.mycode.app.category.service.CategoryQueryService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CategoryCommandService categoryCommandService;

    @MockitoBean
    CategoryQueryService categoryQueryService;

    @Test
    void createCategoryReturns201WithTheNewCategory() throws Exception {
        // Arrange
        UUID id = UUID.fromString("7c9e6679-7425-40de-944b-e07fc1f90ae7");
        when(categoryCommandService.createCategory(new CategoryCreateRequest("Barber")))
                .thenReturn(new CategoryResponse(id, "Barber"));

        // Act + Assert
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Barber\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Barber"));
    }

    @Test
    void createCategoryAcceptsANameOfExactly50Characters() throws Exception {
        // Arrange
        String name = "x".repeat(50);
        when(categoryCommandService.createCategory(any()))
                .thenReturn(new CategoryResponse(UUID.randomUUID(), name));

        // Act + Assert
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + name + "\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createCategoryReturns400WhenTheNameIsBlank() throws Exception {
        // Act + Assert
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"   \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.errors.name").value("Name is required"));
        verify(categoryCommandService, never()).createCategory(any());
    }

    @Test
    void createCategoryReturns400WhenTheNameIsMissing() throws Exception {
        // Act + Assert
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").value("Name is required"));
        verify(categoryCommandService, never()).createCategory(any());
    }

    @Test
    void createCategoryReturns400WhenTheNameIsLongerThan50Characters() throws Exception {
        // Act + Assert
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + "x".repeat(51) + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").value("Name must be at most 50 characters"));
        verify(categoryCommandService, never()).createCategory(any());
    }

    @Test
    void createCategoryReturns400WhenTheBodyIsNotValidJson() throws Exception {
        // Act + Assert
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":"))
                .andExpect(status().isBadRequest());
        verify(categoryCommandService, never()).createCategory(any());
    }

    @Test
    void createCategoryReturns409WhenTheNameAlreadyExists() throws Exception {
        // Arrange
        when(categoryCommandService.createCategory(any())).thenThrow(new CategoryAlreadyExistsException());

        // Act + Assert
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Barber\"}"))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.detail").value("A category with this name already exists"))
                .andExpect(jsonPath("$.instance").value("/api/categories"));
    }
}
