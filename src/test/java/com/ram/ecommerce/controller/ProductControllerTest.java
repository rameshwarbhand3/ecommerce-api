package com.ram.ecommerce.controller;

import com.ram.ecommerce.entity.Product;
import com.ram.ecommerce.repository.ProductRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.hamcrest.core.Is.is;

@SpringBootTest
@Testcontainers
class ProductControllerTest {
    @Container
    private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer(DockerImageName.parse("mysql:latest"));
    @Autowired
    private ProductRepository productRepository;
    @LocalServerPort
    private int port;

    @BeforeAll
    public static void initMySqlContainer() {
        MY_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> MY_SQL_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.username", () -> MY_SQL_CONTAINER.getUsername());
        registry.add("spring.datasource.password", () -> MY_SQL_CONTAINER.getPassword());
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");

    }

    @Test
    public void getAllProductsApiSuccess() {
        Product product = Product.builder()
                .name("Mobile")
                .price(10000)
                .quantity(3)
                .description("Redmi Mobile")
                .build();
        productRepository.save(product);

        RestAssured.given().port(port).get("/api/products")
                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("size()", is(1));


    }
}