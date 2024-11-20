package com.example.test_mobile.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class AuthService {

    public String handleGoogleLogin(String code) {
        // Google 서버로 인증 코드를 보내고 액세스 토큰 획득
        RestTemplate restTemplate = new RestTemplate();
        // Google OAuth 처리 로직 추가
        return "Google login successful";
    }

    public String handleKakaoLogin(String code) {
        // Kakao 서버로 인증 코드를 보내고 액세스 토큰 획득
        RestTemplate restTemplate = new RestTemplate();
        // Kakao OAuth 처리 로직 추가
        return "Kakao login successful";
    }
}
