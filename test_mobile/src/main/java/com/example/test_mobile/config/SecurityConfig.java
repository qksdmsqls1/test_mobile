package com.example.test_mobile.config;

import com.example.test_mobile.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // CSRF 비활성화
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/auth/**", "/login/**", "/oauth2/**").permitAll()  // 인증 관련 엔드포인트 접근 허용
                .anyRequest().authenticated()  // 그 외의 요청은 인증 필요
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)  // 주입된 customOAuth2UserService 사용
                )
            );

        return http.build();
    }
}




