package com.example.restapi.controller.exception;

import com.example.restapi.advice.exception.CustomAuthenticationException;
import com.example.restapi.model.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/entrypoint")
    public CommonResult entrypointException() {
        throw new CustomAuthenticationException();
    }

    @GetMapping("/accessdenied")
    public CommonResult accessDeniedException() {
        // 이미 존재하는 Exception 이므로 AccessDeniedException 던지기
        throw new AccessDeniedException("");
    }
}
