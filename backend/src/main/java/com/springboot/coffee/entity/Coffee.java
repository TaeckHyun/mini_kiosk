package com.springboot.coffee.entity;

import com.springboot.config.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Coffee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long coffeeId;

    @Column(nullable = false, unique = true)
    private String korName;

    @Column(nullable = false, unique = true)
    private String engName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, unique = true, length = 3)
    private String coffeeCode;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CoffeeStatus coffeeStatus = CoffeeStatus.COFFEE_FOR_SALE;

    public enum CoffeeStatus {
        COFFEE_FOR_SALE("판매 중"),
        COFFEE_SOLD_OUT("판매 중지");

        @Getter
        private String status;

        CoffeeStatus(String status) {
            this.status = status;
        }
    }
}
