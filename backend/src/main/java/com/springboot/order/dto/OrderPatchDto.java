package com.springboot.order.dto;

import com.springboot.order.entity.Order;
import lombok.Getter;

@Getter
public class OrderPatchDto {
    private long orderId;
    private Order.OrderStatus orderStatus;
}
