package com.ram.ecommerce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse {
    private Long id;

    private String name;

    private Integer price;

    private Integer quantity;

    private String description;

}
