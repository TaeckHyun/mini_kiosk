package com.springboot.order.dto;

import com.springboot.coffee.entity.Coffee;
import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class OrderCoffeeDto {
    @Positive
    private long coffeeId;

    @Positive
    private int quantity;

    public Coffee getCoffee() {
        Coffee coffee = new Coffee();
        coffee.setCoffeeId(coffeeId);
        return coffee;
    }
}
