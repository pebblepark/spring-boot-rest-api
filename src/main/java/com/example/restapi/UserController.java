package com.example.restapi;

import com.example.restapi.entity.User;
import com.example.restapi.repo.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor // Constructor Injection, 해당 어노테이션 대신 @Autowired 를 사용해도 됨
@RestController // 결과값을 JSON으로 출력합니다.
@RequestMapping(value = "/v1")  // 버전관리용
public class UserController {
    private final UserJpaRepo userJpaRepo;

    @GetMapping(value = "/user")
    public List<User> findAllUser() {
        return userJpaRepo.findAll();
    }

    @PostMapping(value = "/user")
    public User save() {
        User user = User.builder()
                .uid("yumi@naver.com")
                .name("유미")
                .build();
        return userJpaRepo.save(user);
    }
}
