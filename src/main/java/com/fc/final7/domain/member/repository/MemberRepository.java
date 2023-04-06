package com.fc.final7.domain.member.repository;

import com.fc.final7.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAndPhone(String email, String phone);

    Optional<Member> findById(Long MemberId);

    Optional<Member> findByNameAndPhone(String name, String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
