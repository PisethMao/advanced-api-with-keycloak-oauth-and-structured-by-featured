package com.co.istad.piseth.spring_web_mvc.features.category;

import com.co.istad.piseth.spring_web_mvc.features.category.dto.CategoryResponse;
import com.co.istad.piseth.spring_web_mvc.features.category.dto.CreateCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category maCreateCategoryRequestToCategory(
            CreateCategoryRequest createCategoryRequest
    );

    CategoryResponse maCategoryToCategoryResponse(Category category);
}