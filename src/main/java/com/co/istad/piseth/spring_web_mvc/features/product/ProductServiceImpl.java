package com.co.istad.piseth.spring_web_mvc.features.product;

import com.co.istad.piseth.spring_web_mvc.features.category.Category;
import com.co.istad.piseth.spring_web_mvc.features.category.CategoryRepository;
import com.co.istad.piseth.spring_web_mvc.features.order.OrderLineRepository;
import com.co.istad.piseth.spring_web_mvc.features.product.dto.*;
import com.co.istad.piseth.spring_web_mvc.util.GenerateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final OrderLineRepository orderLineRepository;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        Category category = categoryRepository
                .findById(request.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
        product.setDescription(request.description());
        product.setCategory(category);
        product.setCode(GenerateUtil.generateUUID());
        product.setAvailable(true);
        product = productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public Page<ProductResponse> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository
                .findAll(pageable)
                .map(productMapper::toProductResponse);
    }

    @Override
    public ProductResponse updateProduct(String code, UpdateProductRequest request) {
        Product product = productRepository.findById(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category with id " + request.categoryId() + " not found!!!"));
        product.setName(request.name());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
        product.setDescription(request.description());
        product.setCategory(category);
        productMapper.updateProductRequestToProduct(request, product, category);
        product = productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public void deleteProduct(String code) {
        Product product = productRepository.findById(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        if (orderLineRepository.existsByProduct_Code(product.getCode()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product is already in use");
        productRepository.delete(product);
    }

    @Override
    public ProductResponse getProductByCode(String code) {
        return productRepository.findById(code)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @Override
    public ProductResponse patchProduct(String code, com.co.istad.piseth.spring_web_mvc.features.product.dto.PatchProductRequest request) {
        Product product = productRepository.findById(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        if (request.name() != null) {
            if (request.name().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be empty");
            }
            product.setName(request.name());
        }
        if (request.price() != null) {
            product.setPrice(request.price());
        }
        if (request.quantity() != null) {
            product.setQuantity(request.quantity());
        }
        if (request.description() != null) {
            if (request.description().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description cannot be empty");
            }
            product.setDescription(request.description());
        }
        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new RuntimeException("Category with id " + request.categoryId() + " not found!!!"));
            product.setCategory(category);
        }
        productMapper.patchProductRequestToProduct(request, product, product.getCategory());
        product = productRepository.save(product);
        return productMapper.toProductResponse(product);
    }
}
