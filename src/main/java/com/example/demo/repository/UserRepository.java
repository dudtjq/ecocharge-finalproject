package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    // 이메일 중복 체크
//    @Query("SELECT COUNT(*) FROM User u WHERE u.email =: email") -> JPQL
    boolean existsByidentify(String identify);



    boolean existsByPhoneNumber(String phoneNumber);

   User findByPhoneNumber(String PhoneNumber);


    // 리프레시 토큰으로 사용자 정보 조회하기
    Optional<User> findByRefreshToken(String refreshToken);

    Optional<User> findByEmail(String email);

    User findByIdentify(String id);

    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber")
    User findByPassword(String phoneNumber);



//    Optional<User> ChangePw(String phoneNumber, String newPassword);

}
