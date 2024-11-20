package com.example.test_mobile.controller;

import org.springframework.web.bind.annotation.*;
import com.example.test_mobile.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/google")
    public String googleLogin(@RequestParam String code) {
        return authService.handleGoogleLogin(code);
    }

    @GetMapping("/kakao")
    public String kakaoLogin(@RequestParam String code) {
        return authService.handleKakaoLogin(code);
    }
}
