package com.example.demo.qnaapi;

import com.example.demo.entity.User;
import com.example.demo.qnaapi.entity.Qna;
import com.example.demo.qnaapi.repository.QnaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@Transactional
@Rollback(false)
class QnaRepositoryTest {

    @Autowired
    QnaRepository qnaRepository;

    @Autowired
    EntityManager em;

    JPAQueryFactory factory;

     @Test
     @DisplayName("QnA 정상적으로 적용이 되는지 테스트")
     void bulkInsert() {

//         User user = new User();
//
//         user.setId("c02c6193-f40f-4ca3-b1f8-ab73e780366d");

         for (int i = 1; i < 31; i++) {
             qnaRepository.save(
                     Qna.builder()
                             .qnaNo((long) i)
                             .qTitle("QnA 테스트 제목" + i)
                             .qContent("QnA 테스트 내용" + i)
//                             .user(user)
                             .build()
             );

         }

     }


}