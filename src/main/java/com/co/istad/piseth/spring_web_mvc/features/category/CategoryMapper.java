package com.co.istad.piseth.spring_web_mvc.features.category;

import com.co.istad.piseth.spring_web_mvc.features.category.dto.CategoryResponse;
import com.co.istad.piseth.spring_web_mvc.features.category.dto.CreateCategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category maCreateCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest);

    CategoryResponse maCategoryToCategoryResponse(Category category);
}
