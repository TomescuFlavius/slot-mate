package ro.mycode.app.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.mycode.app.TestcontainersConfiguration;
import ro.mycode.app.category.model.Category;
import ro.mycode.app.category.repository.CategoryRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class CategoryIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    void cleanDatabase() {
        categoryRepository.deleteAll();
    }

    @Test
    void createCategorySavesTheCategoryWithAGeneratedUuid() throws Exception {
        // Act
        String body = mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Barber\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Assert
        List<Category> categories = categoryRepository.findAll();
        assertThat(categories).hasSize(1);
        assertThat(categories.get(0).getName()).isEqualTo("Barber");
        assertThat(body).contains(categories.get(0).getId().toString());
    }

    @Test
    void createCategoryReturns409ForTheSameNameWithDifferentCase() throws Exception {
        // Arrange
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Barber\"}"))
                .andExpect(status().isCreated());

        // Act + Assert
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"barber\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail").value("A category with this name already exists"));
        assertThat(categoryRepository.count()).isEqualTo(1);
    }
}
