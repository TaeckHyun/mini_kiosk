package com.springboot.order.repository;

import com.springboot.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

// JPA를 상속 받음으로써 JPA 사용, 형식은 JpaRepository<엔티티 클래스, 엔티티의 기본키 타입>
// 즉 엔티티 클래스를 적으면 데이터베이스 테이블과 매핑되는 클래스를 지정하는 것
public interface OrderRepository extends JpaRepository<Order, Long> {
}
