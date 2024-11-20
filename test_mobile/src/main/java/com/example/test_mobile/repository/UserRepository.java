package com.example.test_mobile.repository;

import com.example.test_mobile.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일을 통한 사용자 조회 메서드
    User findByEmail(String email);
}