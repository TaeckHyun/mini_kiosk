package com.springboot.member.dto;

import com.springboot.member.entity.Member;
import com.springboot.stamp.Stamp;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberResponseDto {
    private long memberId;
    private String email;
    private String name;
    private String phone;
    private Member.MemberStatus memberStatus;
    private int stampCount;

    public String getMemberStatus() {
        return memberStatus.getStatus();
    }
}