package com.example.restapi.service.security;

import com.example.restapi.advice.exception.CUserNotFoundException;
import com.example.restapi.repo.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/* 토큰에 세팅된 유저 정보로 회원정보를 조회하는 UserDetailsService 를 재정의 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserJpaRepo userJpaRepo;

    @Override
    public UserDetails loadUserByUsername(String userPk) {
        return userJpaRepo.findById(Long.valueOf(userPk)).orElseThrow(CUserNotFoundException::new);
    }
}
