package ro.mycode.app.category.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.app.category.dtos.CategoryCreateRequest;
import ro.mycode.app.category.dtos.CategoryResponse;
import ro.mycode.app.category.dtos.CategoryResponseList;
import ro.mycode.app.category.dtos.CategoryUpdateRequest;
import ro.mycode.app.category.exceptions.CategoryNotFoundException;
import ro.mycode.app.category.service.CategoryCommandService;
import ro.mycode.app.category.service.CategoryQueryService;

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


    @DeleteMapping("/delete/{name}")
    public ResponseEntity<CategoryResponse> deleteCategory(@Valid @PathVariable() String name) {
        log.info("HTTP DELETE /api/categories/delete/{} name={}", name);
        return ResponseEntity.ok().body(categoryCommandService.deleteCategory(name));
    }

    @GetMapping("/all")
    public ResponseEntity<CategoryResponseList> findAllCategories() {
        log.info("HTTP GET /api/categories/all");
        return ResponseEntity.status(HttpStatus.OK).body(categoryQueryService.getAllCategories());
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryCreateRequest categoryCreateRequest) {
        log.info("HTTP POST /api/categories/add");
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryCommandService.createCategory(categoryCreateRequest));
    }

    @PutMapping("/update/{name}")
    public ResponseEntity<CategoryResponse> updateCategory(@Valid @PathVariable String name, @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        log.info("HTTP PUT /api/categories/update/{} name={}", name, categoryUpdateRequest.name());
        return ResponseEntity.ok(categoryCommandService.updateCategory(name,categoryUpdateRequest));
    }


}
