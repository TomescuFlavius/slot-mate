package ro.mycode.app.category.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.mycode.app.category.dtos.CategoryCreateRequest;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.dtos.CategoryResponseList;
import ro.mycode.app.category.dtos.CategoryUpdateRequest;
import ro.mycode.app.category.service.CategoryCommandService;
import ro.mycode.app.category.service.CategoryQueryService;

@Controller
@RequestMapping("/api/categories")
@Slf4j
public class CategoryController {
    private CategoryCommandService categoryCommandService;
    private CategoryQueryService categoryQueryService;

    public CategoryController(CategoryCommandService categoryCommandService, CategoryQueryService categoryQueryService) {
        this.categoryCommandService = categoryCommandService;
        this.categoryQueryService = categoryQueryService;

    }

    @GetMapping("/all")
    public ResponseEntity<CategoryResponseList> findAllCategories() {
        log.info("HTTP /api/v1/categories/all");
        return ResponseEntity.status(HttpStatus.OK).body(categoryQueryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryCreateRequest categoryCreateRequest) {
        log.info("HTTP POST /api/categories");
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryCommandService.createCategory(categoryCreateRequest));
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable String name) {
        log.info("HTTP /api/v1/categories/delete");
        return ResponseEntity.status(HttpStatus.OK).body(categoryCommandService.deleteCategory(name));
    }

    @PatchMapping("/update/{email}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable String email, @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        log.info("HTTP /api/v1/categories/update");
        return ResponseEntity.status(HttpStatus.OK).body(categoryCommandService.updateCategory(email, categoryUpdateRequest));
    }



}
