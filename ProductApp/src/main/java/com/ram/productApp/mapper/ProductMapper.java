package com.ram.productApp.mapper;

import com.ram.productApp.dto.ProductRequest;
import com.ram.productApp.dto.ProductResponse;
import com.ram.productApp.entity.Product;

public class ProductMapper {
    public static ProductResponse mapToProductDto(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .build();
    }

    public static Product mapToProduct(ProductRequest productRequest){
        Product product  = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setDescription(productRequest.getDescription());
        return product;
    }
}
