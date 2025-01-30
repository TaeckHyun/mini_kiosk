package com.springboot.order.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderCoffeeResponseDto {
    private long coffeeId;
    private String korName;
    private String engName;
    private Integer price;
    private Integer quantity;
}
