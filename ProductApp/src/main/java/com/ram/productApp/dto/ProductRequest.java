package com.ram.productApp.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class ProductRequest {
    private String name;

    private Integer price;

    private Integer quantity;

    private String description;

}
