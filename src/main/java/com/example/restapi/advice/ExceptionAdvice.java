package com.example.restapi.advice;

import com.example.restapi.model.response.CommonResult;
import com.example.restapi.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/*
 * ControllerAdvice 의 annotation 은  @ControllerAdvice @RestControllerAdvice 두가지
 * 예외 발생 시 json 형태로 결과를 반환 => @RestControllerAdvice 선언
 * annotation 에 추가로 패키지를 적용하면 위에서 설명한 것처럼 특정 패키지 하위의 Controller에만 로직 적용 가능
 * ex) @RestControllerAdvice(basePackages = “com.rest.api”)
 *  */
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)  // Exception 이 발생하면 해당 Handler 로 처리하겠다고 명시하는 annotation
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)   // 해당 Exception 이 발생하면 Response 에 출력되는 HttpStatus Code 가 500 이 되도록 설정
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(); // CommonResult 의 실패 결과를 json 형태로 출력하도록 설정
    }

}
