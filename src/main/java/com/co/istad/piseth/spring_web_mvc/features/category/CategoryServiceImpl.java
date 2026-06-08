package com.co.istad.piseth.spring_web_mvc.features.category;

import com.co.istad.piseth.spring_web_mvc.features.category.dto.CategoryResponse;
import com.co.istad.piseth.spring_web_mvc.features.category.dto.CreateCategoryRequest;
import com.co.istad.piseth.spring_web_mvc.features.category.dto.PatchCategoryRequest;
import com.co.istad.piseth.spring_web_mvc.features.category.dto.UpdateCategoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createNewCategory(CreateCategoryRequest createCategoryRequest) {
        log.info("createNewCategory {}", createCategoryRequest);
        Category category = categoryMapper
                .maCreateCategoryRequestToCategory(createCategoryRequest);
        category = categoryRepository.save(category);
        return categoryMapper.maCategoryToCategoryResponse(category);
    }

    @Override
    public Page<CategoryResponse> getCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(categoryMapper::maCategoryToCategoryResponse);
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return categoryMapper.maCategoryToCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest updateCategoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        category.setName(updateCategoryRequest.name());
        category.setDescription(updateCategoryRequest.description());
        category.setImage(updateCategoryRequest.image());
        category = categoryRepository.save(category);
        return categoryMapper.maCategoryToCategoryResponse(category);
    }

    @Override
    public CategoryResponse patchCategoryById(Integer id, PatchCategoryRequest patchCategoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
        if (patchCategoryRequest.name() != null) {
            category.setName(patchCategoryRequest.name());
        }
        if (patchCategoryRequest.description() != null) {
            category.setDescription(patchCategoryRequest.description());
        }
        if (patchCategoryRequest.image() != null) {
            category.setImage(patchCategoryRequest.image());
        }
        category = categoryRepository.save(category);
        return categoryMapper.maCategoryToCategoryResponse(category);
    }

    @Override
    public void deleteCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
        categoryRepository.delete(category);
    }
}
