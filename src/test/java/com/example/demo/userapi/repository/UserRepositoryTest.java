package com.example.demo.userapi.repository;

import com.example.demo.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("생성이 잘되는지 테스트")
    void testSave() {

        userRepository.save(
                User.builder()
                        .email("aaa1111@naver.com")
                        .userName("김춘식")
                        .phoneNumber("010-0123-4567")
                        .joinDate(LocalDateTime.now())
                        .accessToken("as5d1q65w1dq6sd1as")
                        .accessToken("a+65s21d5a6w51q2w1d3asd")
                        .accessToken("as541d5a3s1xaw1dqwd")
                        .refreshToken("a5s4d8qw987q8w7eq9we7qq")
                        .loginMethod(User.LoginMethod.KAKAO)
                        .profileImg("C://Myworkspace")
                        .refreshTokenExpiryDate(new Date())
                        .build()
        );

        }
}