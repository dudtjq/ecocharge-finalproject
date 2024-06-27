package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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

    User findByIdentify(String Identify);

    @Query("SELECT u.identify FROM User u WHERE u.phoneNumber = :phoneNumber")
    User showedId(String phoneNumber);


    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.phoneNumber = :phoneNumber")
    User updatePasswordByPhoneNumber(String phoneNumber, String newPassword);




//    Optional<User> ChangePw(String phoneNumber, String newPassword);

}
