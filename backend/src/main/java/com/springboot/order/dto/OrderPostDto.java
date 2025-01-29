package com.springboot.order.dto;

import lombok.Getter;

import javax.validation.constraints.Positive;
import java.util.List;

@Getter
public class OrderPostDto {
    @Positive
    private long memberId;

    private List<OrderCoffeeDto> orderCoffeeDtos;
}
