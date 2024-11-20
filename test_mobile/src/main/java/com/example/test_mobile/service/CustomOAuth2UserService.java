package com.example.test_mobile.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        // 기본 사용자 정보를 로드하고, 추가적인 사용자 정보를 로드하거나 가공합니다.
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        // 사용자 정보를 데이터베이스에 저장하거나 추가적인 처리 로직을 수행할 수 있습니다.
        // 여기에서 필요한 작업을 수행한 후 OAuth2User를 반환합니다.

        return oAuth2User;
    }
}
