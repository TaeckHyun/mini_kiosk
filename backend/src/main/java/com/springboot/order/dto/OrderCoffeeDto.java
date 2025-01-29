package com.springboot.order.dto;

import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class OrderCoffeeDto {
    @Positive
    private long coffeeId;

    private int quantity;


}
