package com.example.demo.repository;

import com.example.demo.entity.QnaReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QnaReplyRepository extends JpaRepository<QnaReply, Long> {

    @Query("SELECT COUNT(*) FROM QnaReply r WHERE r.qnaNo = :qnaNo")
    int countByReply(@Param("qnaNo") Long qnaNo);

}
