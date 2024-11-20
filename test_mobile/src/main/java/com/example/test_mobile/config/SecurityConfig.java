package com.example.test_mobile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/auth/**").permitAll()  // Google, Kakao 인증 엔드포인트 접근 허용
                .anyRequest().authenticated()  // 그 외의 요청은 인증 필요
            .and()
            .oauth2Login();

        return http.build();
    }
}
