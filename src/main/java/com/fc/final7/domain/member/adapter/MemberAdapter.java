package com.fc.final7.domain.member.adapter;


import com.fc.final7.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class MemberAdapter extends User {

    private Member member;

    public MemberAdapter(Member member) {
        super(member.getEmail(), member.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        this.member = member;  // MemberDetailService에서 찾아온 member를 세팅
    }
}
