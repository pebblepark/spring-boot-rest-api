package com.example.restapi.repo;

import com.example.restapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {
    // 회원 가입 시 가입한 이메일 조회를 위해 다음 메서드를 선언
    Optional<User> findByUid(String email);
}
