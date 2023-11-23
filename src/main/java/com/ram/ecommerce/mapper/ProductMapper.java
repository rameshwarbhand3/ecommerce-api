package com.ram.ecommerce.mapper;

import com.ram.ecommerce.dto.ProductRequest;
import com.ram.ecommerce.dto.ProductResponse;
import com.ram.ecommerce.entity.Product;

public class ProductMapper {
    public static ProductResponse mapToProductDto(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .build();
    }

    public static Product mapToProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setDescription(productRequest.getDescription());
        return product;
    }
}
