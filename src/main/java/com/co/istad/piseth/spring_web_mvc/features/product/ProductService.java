package com.co.istad.piseth.spring_web_mvc.features.product;

import com.co.istad.piseth.spring_web_mvc.features.product.dto.*;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);

    Page<ProductResponse> getProducts(int page, int size);

    ProductResponse updateProduct(String code, UpdateProductRequest request);

    void deleteProduct(String code);

    ProductResponse getProductByCode(String code);

    ProductResponse patchProduct(String code, com.co.istad.piseth.spring_web_mvc.features.product.dto.PatchProductRequest request);
}
