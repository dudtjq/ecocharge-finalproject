package com.example.demo.service;

import com.example.demo.dto.request.QnaReplyRequestDTO;
import com.example.demo.dto.request.QnaReplyUpdateRequestDTO;
import com.example.demo.dto.response.*;
import com.example.demo.entity.QnaReply;
import com.example.demo.repository.QnaReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class QnaReplyService {

    private final QnaReplyRepository qnaReplyRepository;


    // qna 댓글 생성
    public QnaReplyListResponseDTO create(QnaReplyRequestDTO requestDTO) {

        qnaReplyRepository.save(requestDTO.toEntity());

        return getList(requestDTO.getQnaNo());
    }

    // 하나의  qna 상세 보기
    public QnaReplyDetailResponseDTO qnaReplyDetail(final Long replyNo) {

        final QnaReply qnaReply = qnaReplyRepository.findById(replyNo).orElseThrow(
                () -> new RuntimeException("존재하지 않는 QnA 댓글입니다.")
        );

        return new QnaReplyDetailResponseDTO(qnaReply);

    }


    // qna 댓글 목록 조회
    public QnaReplyListResponseDTO getList(final Long qnaNo){

        List<QnaReply> replyList = qnaReplyRepository.findAll();

        List<QnaReplyDetailResponseDTO> dtoList = replyList.stream()
                .map(QnaReplyDetailResponseDTO::new)
                .toList();

        int byReply = qnaReplyRepository.countByReply(qnaNo);

        return QnaReplyListResponseDTO.builder()
                .replies(dtoList)
                .count(byReply)
                .build();

    }

    public QnaReplyListResponseDTO deleteReply(Long replyNo) {

        qnaReplyRepository.findById(replyNo).orElseThrow(
                () -> {
                    log.error("댓글이 존재하지 않아 삭제에 실패하였습니다. replyNo : {}", replyNo);
                    throw new RuntimeException("댓글이 존재하지 않아 삭제에 실패하였습니다.");
                }
        );

        qnaReplyRepository.deleteById(replyNo);
        return getList(replyNo);
    }

//    public QnaReplyDetailResponseDTO update(final QnaReplyUpdateRequestDTO requestDTO) {
//
//
//        final Optional<QnaReply> byId = qnaReplyRepository.findById(requestDTO.getReplyNo());
//
//        log.info("replyNo : {}", requestDTO.getReplyNo());
//        log.info("replyText : {}", requestDTO.getReplyText());
//
//        byId.ifPresent(qnaReply -> {
//
//            log.info("Found Reply - before update : {}", qnaReply);
//
//            qnaReply.setReplyText(requestDTO.getReplyText());
//
//            qnaReply.setUpdateReplyDate(LocalDateTime.now());
//
//            qnaReplyRepository.save(qnaReply);
//        });
//
//        return qnaReplyDetail(requestDTO.getReplyNo());
//    }
}
