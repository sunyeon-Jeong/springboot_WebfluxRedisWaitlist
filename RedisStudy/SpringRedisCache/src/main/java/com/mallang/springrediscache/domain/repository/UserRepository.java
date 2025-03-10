package com.mallang.springrediscache.domain.repository;

import com.mallang.springrediscache.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}