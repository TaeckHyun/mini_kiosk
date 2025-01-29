package com.springboot.order.controller;

import com.springboot.order.dto.OrderPatchDto;
import com.springboot.order.dto.OrderPostDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {
    @PostMapping
    public ResponseEntity postOrder(@RequestBody OrderPostDto orderPostDto) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{order-id}")
    public ResponseEntity patchOrder(@PathVariable("order-id") long orderId,
                                     @RequestBody OrderPatchDto orderPatchDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@PathVariable("order-id") long orderId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getOrders(@RequestParam @Positive int page,
                                    @RequestParam @Positive int size) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity deleteOrder(@PathVariable("order-id") long orderId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
