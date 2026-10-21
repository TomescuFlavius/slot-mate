package ro.mycode.app.category.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ro.mycode.app.category.dtos.CategoryCreateRequest;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.exceptions.CategoryAlreadyExistsException;
import ro.mycode.app.category.service.CategoryCommandService;
import ro.mycode.app.category.service.CategoryQueryService;
import tools.jackson.databind.json.JsonMapper;

import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ro.mycode.app.constants.ExceptionConstants.CATEGORY_ALREADY_EXISTS_EXCEPTION;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    private static final String URL = "/api/categories";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JsonMapper jsonMapper;

    @MockitoBean
    CategoryCommandService categoryCommandService;

    @MockitoBean
    CategoryQueryService categoryQueryService;

    @Test
    void createCategoryReturns201WithTheNewCategory() throws Exception {
        // Arrange
        CategoryResponse response = new CategoryResponse(UUID.randomUUID(), "Barber");
        when(categoryCommandService.createCategory(new CategoryCreateRequest("Barber"))).thenReturn(response);

        // Act + Assert
        postCategory("Barber")
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonMapper.writeValueAsString(response)));
    }

    @Test
    void createCategoryAcceptsANameOf50Characters() throws Exception {
        // Arrange
        String name = "x".repeat(50);
        when(categoryCommandService.createCategory(any())).thenReturn(new CategoryResponse(UUID.randomUUID(), name));

        // Act + Assert
        postCategory(name).andExpect(status().isCreated());
    }

    static Stream<String> invalidNames() {
        return Stream.of(null, "", "   ", "x".repeat(51));
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void createCategoryReturns400ForAnInvalidName(String name) throws Exception {
        // Act + Assert
        postCategory(name)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.errors.name").exists());
        verifyNoInteractions(categoryCommandService);
    }

    @Test
    void createCategoryReturns400WhenTheBodyIsNotJson() throws Exception {
        // Act + Assert
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content("{"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(categoryCommandService);
    }

    @Test
    void createCategoryReturns409WhenTheNameAlreadyExists() throws Exception {
        // Arrange
        when(categoryCommandService.createCategory(any())).thenThrow(new CategoryAlreadyExistsException());

        // Act + Assert
        postCategory("Barber")
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value(CATEGORY_ALREADY_EXISTS_EXCEPTION));
    }

    private ResultActions postCategory(String name) throws Exception {
        return mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(new CategoryCreateRequest(name))));
    }
}
