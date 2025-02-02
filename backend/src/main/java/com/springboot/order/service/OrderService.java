package com.springboot.order.service;

import com.springboot.coffee.service.CoffeeService;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.service.MemberService;
import com.springboot.order.entity.Order;
import com.springboot.order.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final CoffeeService coffeeService;

    // 의존성 주입(DI), 필요한 클래스들을 의존성 주입을 통해 받아옴
    public OrderService(OrderRepository orderRepository, MemberService memberService, CoffeeService coffeeService) {
        this.orderRepository = orderRepository;
        this.memberService = memberService;
        this.coffeeService = coffeeService;
    }

    public Order createOrder(Order order) {
        // 주문이라는 건 주문한 "회원"이 존재하는지 부터 확인해야함
        // 그래서 회원이 존재하는지 검증하는 검증메서드를 불러오는데 여기에 파라미터로 order 가 가지고 있는 member안에 memberId를 넣음
        // 그러면 주문을 한 회원이 존재하는지 검증함
        Member member = memberService.findVerifiedMember(order.getMember().getMemberId());

        // 커피가 존재하는지 확인해야함
        // OrderCoffees를 가져오는 이유는 Order와 Coffee는 N:M 관계 즉, OrderCoffee가 조인테이블 역할을 하고 있음
        // 하나의 주문에 여러개의 커피가 있을 수 있으니 커피 정보를 가지고 있는 OrderCoffee를 순회하는 것
        order.getOrderCoffees()
                .forEach(orderCoffee -> coffeeService.findVerifiedCoffee(orderCoffee.getCoffee().getCoffeeId()));

        // 주문한 커피의 총 수량을 가져오기 위해 사용되는 메서드
        // order 객체 내부에 List<OrderCoffee> 를 순회하기 위해 get으로 가져와서 stream으로 순회
        int totalQuantity = order.getOrderCoffees().stream()
                // mapToInt는 스트림의 각 요소를 int 값으로 변환함
                // List 내부의 OrderCoffee 객체를 하나씩 돌면서 개수를 intStream 타입으로 가져옴
                .mapToInt(OrderCoffee -> OrderCoffee.getQuantity())
                .sum();

        // member 내부에 있는 stamp 안에 있는 addStampCount를 통해 stamp 객체 안에 있는 stampCount 증가
        member.getStamp().addStampCount(totalQuantity);

        // 검증이 끝난 order 객체를 DB에 저장하고, 저장 결과(엔티티 객체)를 반환해줌
        return orderRepository.save(order);
    }


    public Order updateOrder(Order order) {
        // 주문상태를 변경하기 위해선 먼저 주문이 있는지부터 봐야함
        Order findOrder = findVerifiedOrder(order.getOrderId());
        // orderStatus가 null일 수도 있으니 ofNullable을 통해 Optional로 감싸줌
        // 만약 Optional 내부 값이 null이면 Optional.empty()를 반환함
        Optional.ofNullable(order.getOrderStatus())
                // ifPresent는 Optional 내부 값이 있다면 람다식을 실행함
                // 만약 내부 값이 없다면 아무 것도 하지 않음
                // 람다식을 실행하면 findOrder의 상태를 orderStatus로 변경
                .ifPresent(orderStatus -> findOrder.setOrderStatus(orderStatus));

        return orderRepository.save(findOrder);
    }

    public Order findOrder(Long orderId) {
        return findVerifiedOrder(orderId);
    }

    // PageRequest.of() 메서드를 사용하여 페이지 정보를 담은 Pageable 객체 생성
    // Page<Order> 안에는 List<Order>와 page, size 정보가 다 들어있음
    // PageRequest는 Pageable 인터페이스를 구현한 클래스임
    // Pageable은 페이지네이션을 위한 정보를 담고 있는 인터페이스
    public Page<Order> findOrders(int page, int size) {
        // page, size로 페이지 번호, 한 페이지에 포함할 데이터 수를 넣음
        // orderId를 기준으로 내림차순 정렬하여 데이터를 가져옴
        return orderRepository.findAll(PageRequest.of(page, size, Sort.by("orderId").descending()));
    }

    public void cancelOrder(Long orderId) {
        // 주문이 있는지 먼저 검증 해야함
        Order findOrder = findVerifiedOrder(orderId);

        // 주문이 있다면 가져온 주문에서 status에 status를 표시하는 수를 가져옴
        int statusNumber = findOrder.getOrderStatus().getStepNumber();

        /* 만약 1. 주문 요청 상태가 아닌 2. 주문 확정, 3. 주문 처리 완료, 4. 주문 취소 상태 이 3개중 하나 상태라면
        그 상태에서는 주문을 취소 한다는게 말이 안됨 즉 예외처리 해줘야함
        */
        if(statusNumber > 1) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_ORDER);
        }
        // 주문 요청 상태 라면 취소 상태로 변경
        findOrder.setOrderStatus(Order.OrderStatus.ORDER_CANCEL);

        // 상태 저장
        orderRepository.save(findOrder);
    }

    public Order findVerifiedOrder(long orderId) {
        // JpaRepository는 Optional로 감싸서 반환해줌. 값이 없을 수도 있을 수도 있으니까, NPE 방지
        Optional<Order> order = orderRepository.findById(orderId);
        // 값이 있다면 Optional 내부 값 반환, 없다면 예외처리
        return order.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
    }
}