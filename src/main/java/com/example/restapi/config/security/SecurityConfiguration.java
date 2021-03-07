package com.example.restapi.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .csrf().disable() // rest api 이므로 csrf 보안이 필요없으므로 disable 처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token 으로 인증하므로 세션은 필요없으므로 생성안함.
                .and()
                .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
                    .antMatchers("/*/signin", "/*/signup").permitAll() // 가입 및 인증 주소는 누구나 접근가능
                    .antMatchers(HttpMethod.GET, "helloworld/**", "/exception/**").permitAll() // helloworld 로 시작하는 GET 요청 리소스는 누구나 접근가능
                    .antMatchers("/*/users").hasRole("ADMIN")
                .anyRequest().hasRole("USER") // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt token 필터를 id/password 인증 필터 전에 넣는다
    }
    @Override // ignore check swagger resource
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }
}

/*
* 아무나 접근 가능한 리소스는 permitAll()로 세팅하고 나머지 리소스는 다음과 같이 ‘ROLE_USER’ 권한이 필요함으로 명시
* anyRequest().hasRole(“USER”) 또는 anyRequest().authenticated()는 동일하게 동작함

* JwtAuthenticationFilter 는 UsernamePasswordAuthenticationFilter 앞에 설정해야함
->  클라이언트가 리소스를 요청할 때 접근 권한이 없는 경우 기본적으로 로그인 폼으로 보내게 되는데 그 역할을 하는 필터는 UsernamePasswordAuthenticationFilter
-> Rest Api 에서는 로그인 폼이 따로 없으므로 인증 권한이 없다는 오류 Json 을 내려줘야 하므로 UsernamePasswordAuthenticationFilter 전에 관련 처리를 넣어야 함

* SpringSecurity 적용 후에는 모든 리소스에 대한 접근이 제한되므로. Swagger 페이지에 대해서는 예외를 적용해야 페이지에 접근이 가능함
* 리소스 접근 제한 표현식은 여러 가지가 있으며 다음과 같음

hasIpAddress(ip) – 접근자의 IP 주소가 매칭 하는지 확인
hasRole(role) – 역할이 부여된 권한(Granted Authority)과 일치하는지 확인
hasAnyRole(role) – 부여된 역할 중 일치하는 항목이 있는지 확인
ex) access = “hasAnyRole(‘ROLE_USER’,’ROLE_ADMIN’)”
permitAll – 모든 접근자를 항상 승인
denyAll – 모든 사용자의 접근을 거부
anonymous – 사용자가 익명 사용자인지 확인
authenticated – 인증된 사용자인지 확인
rememberMe – 사용자가 remember me를 사용해 인증했는지 확인
fullyAuthenticated – 사용자가 모든 크리덴셜을 갖춘 상태에서 인증했는지 확인
*
* */