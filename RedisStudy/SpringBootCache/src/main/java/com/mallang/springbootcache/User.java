package com.mallang.springbootcache;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder // 데이터 save하기 위한 lombok 양식
@Getter
@NoArgsConstructor // 파라미터가 없는 기본 생성자 추가 (값 초기화X. 객체생성 후 필드 세팅을 도와줌)
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가 (Builder와 함께 특정 필드를 선택적으로 초기화)
@EntityListeners(AuditingEntityListener.class) // created, modified랑 연결 (자동 값 갱신)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String email;

    @Column(length = 30)

    private String name;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}