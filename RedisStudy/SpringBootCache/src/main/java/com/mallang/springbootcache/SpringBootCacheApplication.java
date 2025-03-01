package com.mallang.springbootcache;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// ApplicationRunner -> application 시작 시, 데이터 삽입
@SpringBootApplication
@RequiredArgsConstructor // final, @Notnull 필드 값만 파라미터로 받는 생성자 자동생성
public class SpringBootCacheApplication implements ApplicationRunner {

    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCacheApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.save(User.builder().name("mallang").email("mallang@mallang.com").build());
        userRepository.save(User.builder().name("chunsik").email("chunsik@chunsik.com").build());
        userRepository.save(User.builder().name("ryan").email("ryan@ryan.com").build());
        userRepository.save(User.builder().name("grommit").email("grommit@grommit.com").build());
    }

}