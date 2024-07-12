package com.example.demo.repository;


import com.example.demo.entity.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BoardReplyRepository extends JpaRepository<BoardReply, Long> {

    @Query("SELECT r FROM BoardReply r WHERE r.board.boardNo = :boardNo")
    List<BoardReply> findByBoard(Long boardNo);


    @Transactional
    void deleteByReplyNoAndUserId(Long replyNo, String userId);
}
