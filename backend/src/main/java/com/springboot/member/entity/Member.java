package com.springboot.member.entity;

import com.springboot.config.BaseEntity;
import com.springboot.order.entity.Order;
import com.springboot.stamp.Stamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 13)
    private String phone;

    @Enumerated(value = EnumType.STRING)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    // CascadeType.PERSIST, REMOVE를 통해 member가 영속성 컨텍스트에 올라가면 stamp도 같이 올라감, 지울때도 같이 삭제
    @OneToOne(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "STAMP_ID")
    private Stamp stamp;

    // new ArrayList로 초기화, Null 참조 방지
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    // Stamp와 동기화, 영속성 전이
    public void setStamp(Stamp stamp) {
        // stamp 파라미터로 가지고 있는 stamp
        this.stamp = stamp;
        //
        if(stamp.getMember() != this) {
            stamp.setMember(this);
        }
    }

    public void setOrder(Order order) {
        orders.add(order);
        if (order.getMember() != this) {
            order.setMember(this);
        }
    }

    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) { this.status = status; }
    }
}
