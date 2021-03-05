package com.example.restapi.repo;

import com.example.restapi.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserJpaRepoTest {

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenFindByUid_thenReturnUser() {
        String uid = "yumi@gmail.com";
        String name = "유미";

        //given
        userJpaRepo.save(User.builder()
                .uid(uid)
                .password(passwordEncoder.encode("1234"))
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        //when
        Optional<User> user = userJpaRepo.findByUid(uid);

        //then
        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(user.get().getName(), name);
        assertThat(user.get().getName(), is(name));
    }
}

/*
 * @DataJpaTest : JPA 테스트를 위한 손쉬운 환경을 생성해줌
 * 해당 어노테이션은 다른 컴포넌트들은 로드하지 않고 @Entity 를 읽어 Repository 내용을 테스트할 수 있는 환경을 만들어 줌
 * 또한 @Transactional 을 포함하고 있어 테스트가 완료되면 따로 롤백을 할 필요 x
 * 회원을 생성하고 조회하여 둘 간의 데이터가 맞는지 비교하는 테스트입니다.
 * JPA Repo 의 단위 테스트가 필요하다면 @DataJpaTest 를 이용하는 것이 효과적
 * 참고로 @AutoConfigureTestDatabase(replace = Replace.NONE) 라는 어노테이션의 속성을 추가하면 메모리 데이터 베이스가 아닌 실 데이터베이스에 테스트도 가능
 * */