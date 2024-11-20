package com.example.test_mobile.service;

import com.example.test_mobile.model.User;
import com.example.test_mobile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 사용자 등록 (회원가입) 메서드
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // 모든 사용자 조회 메서드
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 특정 사용자 조회 메서드
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
