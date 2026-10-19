package ro.mycode.slotmateapi.controller.category;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import ro.mycode.app.category.controller.CategoryController;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.dtos.CategoryResponseList;
import ro.mycode.app.category.service.CategoryCommandService;
import ro.mycode.app.category.service.CategoryQueryService;

import java.util.List;

@WebMvcTest(controllers=CategoryController.class)
public class CategoryControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockitoBean
    CategoryCommandService categoryCommandService;
    @MockitoBean
    CategoryQueryService categoryQueryService;

    @Test
    void findAllCategories() throws Exception{
        CategoryResponse categoryResponse1=new CategoryResponse(1L,"nume1");
        CategoryResponse categoryResponse2=new CategoryResponse(2L,"nume2");
        CategoryResponseList categoryResponseList=new CategoryResponseList(List.of(categoryResponse1,categoryResponse2));
        when(categoryQueryService.getAllCategories()).thenReturn(categoryResponseList);

        mockMvc.perform(get("/api/v1/categories/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status.isOk())
                .andExpect(jsonPath("$.categoryResponseList[0].id").value(1L))
                .andExpect(jsonPath("$.category.ResponseList[1].name").value("nume2"));
    }


}
