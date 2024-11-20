package com.example.test_mobile.repository;

import com.example.test_mobile.model.Pill;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository를 확장하여 PillRepository를 생성합니다.
// drugNum을 기준으로 Pill을 조회하는 findByDrugNum 메서드를 추가합니다.
public interface PillRepository extends JpaRepository<Pill, Integer> {

    // drugNum을 기준으로 Pill을 조회하는 메서드 추가
    Pill findByDrugNum(Integer drugNum);  // drugNum으로 Pill 객체 반환
}
