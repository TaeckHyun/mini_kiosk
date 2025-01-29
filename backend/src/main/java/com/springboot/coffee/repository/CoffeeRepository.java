package com.springboot.coffee.repository;

import com.springboot.coffee.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    Optional<Coffee> findByCoffeeCode(String CoffeeCode);
}
