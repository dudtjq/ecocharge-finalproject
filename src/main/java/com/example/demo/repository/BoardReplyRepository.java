package com.example.demo.repository;

import com.example.demo.entity.Board;
import com.example.demo.entity.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Long> {

    @Query("SELECT COUNT(*) FROM BoardReply r WHERE r.boardNo = :boardNo")
    int countByReply(@Param("boardNo") Long boardNo);

}
