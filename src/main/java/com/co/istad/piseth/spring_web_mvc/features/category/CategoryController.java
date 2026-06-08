package com.co.istad.piseth.spring_web_mvc.features.category;

import com.co.istad.piseth.spring_web_mvc.features.category.dto.CategoryResponse;
import com.co.istad.piseth.spring_web_mvc.features.category.dto.CreateCategoryRequest;
import com.co.istad.piseth.spring_web_mvc.features.category.dto.PatchCategoryRequest;
import com.co.istad.piseth.spring_web_mvc.features.category.dto.UpdateCategoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createNewCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        return categoryService.createNewCategory(createCategoryRequest);
    }

    @GetMapping
    public Page<CategoryResponse> getCategories(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "25") int size) {
        return categoryService.getCategories(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategoryById(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateCategoryRequest updateCategoryRequest
    ) {
        return ResponseEntity.ok(categoryService.updateCategoryById(id, updateCategoryRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponse> patchCategoryById(
            @PathVariable Integer id,
            @RequestBody PatchCategoryRequest patchCategoryRequest
    ) {
        return ResponseEntity.ok(categoryService.patchCategoryById(id, patchCategoryRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Integer id) {
        categoryService.deleteCategoryById(id);
    }
}
