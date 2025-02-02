package com.springboot.order.controller;

import com.springboot.response.MultiResponseDto;
import com.springboot.response.SingleResponseDto;
import com.springboot.member.service.MemberService;
import com.springboot.order.dto.OrderPatchDto;
import com.springboot.order.dto.OrderPostDto;
import com.springboot.order.entity.Order;
import com.springboot.order.mapper.OrderMapper;
import com.springboot.order.service.OrderService;
import com.springboot.utils.UriCreator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/v1/orders")
@Validated
public class OrderController {
    private final static String ORDER_DEFAULT_URL = "/v1/orders";
    private final OrderService orderService;
    private final OrderMapper mapper;

    public OrderController(OrderService orderService,
                           OrderMapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postOrder(@Valid @RequestBody OrderPostDto orderPostDto) {
        Order order = orderService.createOrder(mapper.orderPostDtoToOrder(orderPostDto));
        // 새로 생성된 주문의 식별자(order.getOrderId())를 이용해, 주문 리소스의 URI를 생성
        // ORDER_DEFAULT_URL은 주문과 관련된 기본 URL 경로(예: "/orders")를 나타내며,
        // UriCreator.createUri()는 이 기본 경로와 주문의 ID를 결합하여 완전한 URI(예: "/orders/123")를 만듬
        URI location = UriCreator.createUri(ORDER_DEFAULT_URL, order.getOrderId());

        // ResponseEntity.created(location)는 HTTP 상태 코드를 201로 설정하고,
        // Location 헤더에 생성된 리소스의 URI를 포함하도록 빌더를 구성
        // build()를 호출하여 최종 ResponseEntity 객체를 생성한 후 반환
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{order-id}")
    public ResponseEntity patchOrder(@PathVariable("order-id") @Positive long orderId,
                                     @Valid @RequestBody OrderPatchDto orderPatchDto) {
        orderPatchDto.setOrderId(orderId);
        Order order =
                orderService.updateOrder(mapper.orderPatchDtoToOrder(orderPatchDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.orderToOrderResponseDto(order))
                , HttpStatus.OK);
    }
    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@PathVariable("order-id") @Positive long orderId) {
        Order order = orderService.findOrder(orderId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.orderToOrderResponseDto(order)),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getOrders(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<Order> pageOrders = orderService.findOrders(page - 1, size);
        List<Order> orders = pageOrders.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.ordersToOrderResponseDtos(orders), pageOrders),
                HttpStatus.OK);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity cancelOrder(@PathVariable("order-id") @Positive long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
