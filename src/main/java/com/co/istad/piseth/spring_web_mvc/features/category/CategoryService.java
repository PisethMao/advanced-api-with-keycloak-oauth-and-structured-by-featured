package com.co.istad.piseth.spring_web_mvc.features.category;

import com.co.istad.piseth.spring_web_mvc.features.category.dto.CategoryResponse;
import com.co.istad.piseth.spring_web_mvc.features.category.dto.CreateCategoryRequest;
import com.co.istad.piseth.spring_web_mvc.features.category.dto.PatchCategoryRequest;
import com.co.istad.piseth.spring_web_mvc.features.category.dto.UpdateCategoryRequest;
import org.springframework.data.domain.Page;

public interface CategoryService {
    CategoryResponse createNewCategory(CreateCategoryRequest createCategoryRequest);

    Page<CategoryResponse> getCategories(int page, int size);

    CategoryResponse getCategoryById(Integer id);

    CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest updateCategoryRequest);

    CategoryResponse patchCategoryById(Integer id, PatchCategoryRequest patchCategoryRequest);

    void deleteCategoryById(Integer id);
}
