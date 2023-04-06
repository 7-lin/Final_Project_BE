package com.fc.final7.domain.member.oauth.repository;

import com.fc.final7.domain.member.oauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Oauth2UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
