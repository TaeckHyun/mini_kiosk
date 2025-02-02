package com.springboot.stamp;

import com.springboot.config.BaseEntity;
import com.springboot.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Stamp extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long stampId;

    @Column(nullable = false)
    private int stampCount;

    // Member와 1ㄷ1 관계 매핑
    @OneToOne
    @JoinColumn(name = "MEMBER_ID") // member_id로 JOIN
    private Member member;

    // Member와 동기화, 영속성 전이
    public void setMember(Member member) {
        // member 파라미터로 가지고 있는 member 갱신
        this.member = member;
        if(member.getStamp() != this) { /* Member가 가지고 있는 Stamp가 현재 내 Stamp 객체가 맞는지 확인
        즉 최신화 상태가 유지 중인지 확인 하는 것
        */
            member.setStamp(this); // 만약 아니라면 member의 Stamp 상태를 최신화
        }
    }

    // StampCount를 늘리기 위한 함수, OrderService에 구현되어있음
    public void addStampCount(int quantity) {
        this.stampCount += quantity;
    }
}
