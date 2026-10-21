package ro.mycode.app.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ro.mycode.app.TestcontainersConfiguration;
import ro.mycode.app.category.dtos.CategoryCreateRequest;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.model.Category;
import ro.mycode.app.category.repository.CategoryRepository;
import tools.jackson.databind.json.JsonMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class CategoryIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JsonMapper jsonMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    void cleanDatabase() {
        categoryRepository.deleteAll();
    }

    @Test
    void createCategorySavesTheCategoryUnderTheReturnedId() throws Exception {
        // Act
        String body = postCategory("Barber")
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Assert
        CategoryResponse response = jsonMapper.readValue(body, CategoryResponse.class);
        Category saved = categoryRepository.findById(response.id()).orElseThrow();
        assertThat(saved.getName()).isEqualTo("Barber");
    }

    @Test
    void createCategoryReturns409ForTheSameNameWithDifferentCase() throws Exception {
        // Arrange
        postCategory("Barber").andExpect(status().isCreated());

        // Act + Assert
        postCategory("barber").andExpect(status().isConflict());
        assertThat(categoryRepository.count()).isEqualTo(1);
    }

    private ResultActions postCategory(String name) throws Exception {
        return mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(new CategoryCreateRequest(name))));
    }
}
