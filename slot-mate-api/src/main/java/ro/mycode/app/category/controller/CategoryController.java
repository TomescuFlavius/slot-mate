package ro.mycode.app.category.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.app.category.dtos.CategoryCreateRequest;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.service.CategoryCommandService;
import ro.mycode.app.category.service.CategoryQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Slf4j
public class CategoryController {
    private CategoryCommandService categoryCommandService;
    private CategoryQueryService categoryQueryService;

    public CategoryController(CategoryCommandService categoryCommandService, CategoryQueryService categoryQueryService) {
        this.categoryCommandService = categoryCommandService;
        this.categoryQueryService = categoryQueryService;

    }




    @GetMapping("")
    public ResponseEntity<List<CategoryResponse>> findAllCategories() {
        log.info("HTTP GET /api/categories/all");
        return ResponseEntity.status(HttpStatus.OK).body(categoryQueryService.getAllCategories().categoryResponseList());
    }

    @PostMapping()
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryCreateRequest categoryCreateRequest) {
        log.info("HTTP POST /api/categories/");
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryCommandService.createCategory(categoryCreateRequest));
    }




}
