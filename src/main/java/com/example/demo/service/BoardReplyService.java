package com.example.demo.service;

import com.example.demo.dto.request.BoardReplyRequestDTO;
import com.example.demo.dto.request.BoardReplyUpdateRequestDTO;
import com.example.demo.dto.response.BoardReplyDetailResponseDTO;
import com.example.demo.dto.response.BoardReplyListResponseDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.BoardReply;
import com.example.demo.repository.BoardReplyRepository;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardReplyService {

    private final BoardReplyRepository boardReplyRepository;
    private final BoardRepository boardRepository;


    // 게시물 댓글 생성
    public BoardReplyListResponseDTO create(final BoardReplyRequestDTO requestDTO) {
        Board board = boardRepository.findById(requestDTO.getBoardNo()).orElseThrow();

        boardReplyRepository.save(requestDTO.toEntity(board));

        return getList(requestDTO.getBoardNo());

    }

    // 하나의 게시물 상세 보기
    public BoardReplyDetailResponseDTO boardReplyDetail(final Long replyNo) {

        final BoardReply boardReply = boardReplyRepository.findById(replyNo).orElseThrow(
                () -> new RuntimeException("존재하지 않는 게시물 댓글입니다.")
        );

        return new BoardReplyDetailResponseDTO(boardReply);

    }



    public BoardReplyListResponseDTO getList(final Long boardNo){


        log.info("boardNo: {}", boardNo);
        log.info("게시글 탐색");

        final List<BoardReply> replyList = boardReplyRepository.findByBoard(boardNo);
        log.info("replyList: {}", replyList);

        List<BoardReplyDetailResponseDTO> dtoList = replyList.stream()
                .map(BoardReplyDetailResponseDTO::new)
                .toList();
        log.info("dtoList: {}", dtoList);

        return BoardReplyListResponseDTO.builder()
                .replies(dtoList)
                .build();

    }



    public BoardReplyListResponseDTO deleteReply(Long replyNo,String userId) {

        boardReplyRepository.findById(replyNo).orElseThrow(
                () -> {
                    log.error("댓글이 존재하지 않아 삭제에 실패하였습니다. replyNo : {}", replyNo);
                    throw new RuntimeException("댓글이 존재하지 않아 삭제에 실패하였습니다.");
                }
        );

        boardReplyRepository.deleteByReplyNoAndUserId(replyNo,userId);
        return getList(replyNo);
    }



    public BoardReplyDetailResponseDTO update(final BoardReplyUpdateRequestDTO requestDTO) {

        log.info("Request DTO - replyNo : {}, replyText : {}", requestDTO.getReplyNo(), requestDTO.getReplyText());

        Optional<BoardReply> byId = boardReplyRepository.findById(requestDTO.getReplyNo());

        log.info("replyNo : {}", requestDTO.getReplyNo());
        log.info("replyText : {}", requestDTO.getReplyText());

        byId.ifPresent(boardReply -> {

            log.info("Found Reply - before update : {}", boardReply);

            boardReply.setReplyText(requestDTO.getReplyText());

            boardReply.setUpdateReplyDate(LocalDateTime.now());

            log.info("Updated Reply - getReplyText : {}", requestDTO.getReplyText());

            boardReplyRepository.save(boardReply);
        });

        return boardReplyDetail(requestDTO.getReplyNo());
    }
}
