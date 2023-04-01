package com.fc.final7.domain.member.service;

import com.fc.final7.domain.member.adapter.MemberAdapter;
import com.fc.final7.domain.member.entity.Member;
import com.fc.final7.domain.member.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Getter
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        MemberAdapter memberAdapter = new MemberAdapter(member);
        return memberAdapter;
    }
}
