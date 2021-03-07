package com.example.restapi.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendRedirect("/exception/entrypoint");
    }
}

/*
* 온전한 Jwt 가 전달이 안될 경우는 토큰 인증 처리 자체가 불가능하기 때문에, 토큰 검증 단에서 프로세스가 종료됨
* 해당 예외를 잡아내려면 SpringSecurity 에서 제공하는 AuthenticationEntryPoint 를 상속받아 재정의 해야함
* 예외가 발생할 경우 /exception/entrypoint 로 포워딩되도록 처리
* */
