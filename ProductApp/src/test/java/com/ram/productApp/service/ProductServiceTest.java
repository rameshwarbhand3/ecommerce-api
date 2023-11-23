package com.ram.productApp.service;

import com.ram.productApp.dto.CreateProductResponse;
import com.ram.productApp.dto.ProductRequest;
import com.ram.productApp.dto.ProductResponse;
import com.ram.productApp.entity.Product;
import com.ram.productApp.exception.ProductNotAvailableException;
import com.ram.productApp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    private ProductService productService;
    private Product product;

    @BeforeEach
    public void setUp() {
        productService = new ProductService(productRepository);
        product = Product.builder()
                .id(1L)
                .name("Mobile")
                .price(10000)
                .quantity(5)
                .description("Redmi Mobile")
                .build();
    }


    @Test
    public void shouldSaveProducts() {
        //given
        ProductRequest request = ProductRequest.builder()
                .price(10000)
                .name("Mobile")
                .quantity(5)
                .description("Redmi Mobile")
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(product);

        //when
        CreateProductResponse createProductResponse = productService.createProduct(request);

        //then
        assertNotNull(createProductResponse);
        assertEquals(1L, createProductResponse.getId());
    }

    @Test
    @DisplayName("Return All products")
    public void shouldReturnAllProducts() {
        //given
        Product product1 = Product.builder()
                .name("Car")
                .price(20000)
                .quantity(2)
                .description("EECO")
                .build();
        List<Product> productList = List.of(product, product1);
        when(productRepository.findAll()).thenReturn(productList);

        //when
        List<ProductResponse> products = productService.getAllProducts();

        //then
        assertEquals(2, products.size());
    }

    @Test
    @DisplayName("Throw Exception when Products are not available")
    public void shouldThrowExceptionWhenProductsAreEmpty() {
        //given
        when(productRepository.findAll()).thenReturn(List.of());

        //when
        ProductNotAvailableException productNotAvailableException = assertThrows(ProductNotAvailableException.class, () -> productService.getAllProducts());

        //then
        verify(productRepository).findAll();
        assertEquals("Products Not Available", productNotAvailableException.getMessage());
    }

    @Test
    @DisplayName("Return Product with given productId")
    public void shouldReturnProductWithGivenId() {
        //given
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        //when
        ProductResponse productResponse = productService.getProductById(productId);

        //then
        assertEquals(10000, productResponse.getPrice());
    }

    @Test
    public void shouldThrowExceptionWhenGivenProductIdNotPresent() {
        //given
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        //when
        ProductNotAvailableException productNotAvailableException = assertThrows(ProductNotAvailableException.class, () -> productService.getProductById(productId));

        //then
        assertEquals("Product Not Available with given id " + productId, productNotAvailableException.getMessage());
    }

    @Test
    public void shouldDeleteProduct() {
        //given
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        willDoNothing().given(productRepository).deleteById(1L);

        //when
        productService.deleteProduct(productId);

        //then
        verify(productRepository, times(1)).deleteById(productId);

    }

    @Test
    public void shouldUpdateProduct_ReturnUpdatedProduct() {
        //given
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        //when
        ProductRequest productRequest = ProductRequest.builder()
                .price(11000)
                .name("Samsung")
                .quantity(2)
                .description("Redmi Mobile")
                .build();

        ProductResponse updatedProduct = productService.updateProduct(productId, productRequest);

        //then
        assertEquals("Samsung", updatedProduct.getName());
        assertEquals(2, updatedProduct.getQuantity());
        assertEquals(11000, updatedProduct.getPrice());
        assertEquals("Redmi Mobile", updatedProduct.getDescription());
    }

}