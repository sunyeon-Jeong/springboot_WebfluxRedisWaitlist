package com.mallang.springrediscache;

import com.mallang.springrediscache.domain.entity.User;
import com.mallang.springrediscache.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class SpringRedisCacheApplication implements ApplicationRunner {

    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisCacheApplication.class, args);
    }

    // ApplicationRunner : 애플리케이션 시작직후 실행되는 코드블럭
    @Override
    public void run(ApplicationArguments args) throws Exception {

        userRepository.save(User.builder().name("mallang").email("mallang@mallang.com").build());
        userRepository.save(User.builder().name("chunsik").email("chunsik@chunsik.com").build());
        userRepository.save(User.builder().name("grommit").email("grommit@grommit.com").build());

    }

}