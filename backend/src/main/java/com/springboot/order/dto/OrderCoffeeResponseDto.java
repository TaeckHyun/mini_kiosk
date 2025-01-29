package com.springboot.order.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCoffeeResponseDto {
    private long coffeeId;
    private String korName;
    private String engName;
    private Integer price;
    private Integer quantity;
}
