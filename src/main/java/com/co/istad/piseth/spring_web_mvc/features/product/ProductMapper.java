package com.co.istad.piseth.spring_web_mvc.features.product;

import com.co.istad.piseth.spring_web_mvc.features.category.Category;
import com.co.istad.piseth.spring_web_mvc.features.product.dto.PatchProductRequest;
import com.co.istad.piseth.spring_web_mvc.features.product.dto.ProductResponse;
import com.co.istad.piseth.spring_web_mvc.features.product.dto.UpdateProductRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "available", target = "isAvailable")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toProductResponse(Product product);

    @BeanMapping(ignoreByDefault = true)
//    @Mapping(target = "category", source = "category")
//    @Mapping(target = "code", ignore = true)
//    @Mapping(target = "available", ignore = true)
    void updateProductRequestToProduct(UpdateProductRequest request, @MappingTarget Product product, Category category);

    @BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", source = "request.name")
    void patchProductRequestToProduct(PatchProductRequest request, @MappingTarget Product product, Category category);
}
