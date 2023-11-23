package com.ram.productApp.controller;

import com.ram.productApp.dto.CreateProductResponse;
import com.ram.productApp.dto.ProductRequest;
import com.ram.productApp.dto.ProductResponse;
import com.ram.productApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        CreateProductResponse createProductResponse = productService.createProduct(productRequest);
        return new ResponseEntity<>(createProductResponse, HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> allProducts = productService.getAllProducts();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long productId) {
        ProductResponse aProduct = productService.getProductById(productId);
        return new ResponseEntity<>(aProduct, HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long productId, @RequestBody ProductRequest productRequest) {
        ProductResponse productDto1 = productService.updateProduct(productId, productRequest);
        return new ResponseEntity<>(productDto1, HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product deleted Successfully", HttpStatus.NO_CONTENT);
    }

}
