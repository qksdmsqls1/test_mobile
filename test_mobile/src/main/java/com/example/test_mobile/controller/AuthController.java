package com.example.test_mobile.controller;

import org.springframework.web.bind.annotation.*;
import com.example.test_mobile.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // 구글 로그인 핸들러
    @GetMapping("/google")
    public String googleLogin(@RequestParam String code) {
        return authService.handleGoogleLogin(code);
    }

    // 카카오 로그인 핸들러
    @GetMapping("/kakao")
    public String kakaoLogin(@RequestParam String code) {
        return authService.handleKakaoLogin(code);
    }

    // 로그인 성공 시 호출될 엔드포인트
    @GetMapping("/success")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return oAuth2User.getAttributes();  // 로그인된 유저 정보를 반환
    }

    // 로그인 실패 시 호출될 엔드포인트
    @GetMapping("/failure")
    public String loginFailure() {
        return "Login failed. Please try again.";
    }
}
