package com.ram.productApp.service;

import com.ram.productApp.dto.ProductRequest;
import com.ram.productApp.dto.CreateProductResponse;
import com.ram.productApp.dto.ProductResponse;
import com.ram.productApp.exception.ProductNotAvailableException;
import com.ram.productApp.mapper.ProductMapper;
import com.ram.productApp.entity.Product;
import com.ram.productApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public CreateProductResponse createProduct(ProductRequest productRequest) {
        Product product = ProductMapper.mapToProduct(productRequest);
        Product savedProduct = productRepository.save(product);
        return CreateProductResponse.builder().id(savedProduct.getId()).build();
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> productsList = productRepository.findAll();
        if(productsList.isEmpty()){
            throw new ProductNotAvailableException("Products Not Available");
        }

        return productsList.stream().map(ProductMapper::mapToProductDto)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductNotAvailableException("Product Not Available with given id " + productId);
        }
        return ProductMapper.mapToProductDto(product.get());
    }

    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotAvailableException("Product Not Available with given id " + productId));
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setDescription(productRequest.getDescription());
        Product saveProduct = productRepository.save(product);
        return ProductMapper.mapToProductDto(saveProduct);
    }

    public void deleteProduct(Long productId) {
      productRepository.findById(productId)
               .orElseThrow(() -> new ProductNotAvailableException("Product Not Available with given id " + productId));
        productRepository.deleteById(productId);
    }
}
