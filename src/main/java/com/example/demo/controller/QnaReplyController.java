package com.example.demo.controller;

import com.example.demo.dto.request.QnaReplyRequestDTO;
import com.example.demo.dto.response.QnaReplyDetailResponseDTO;
import com.example.demo.dto.response.QnaReplyListResponseDTO;
import com.example.demo.service.QnaReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/ecocharge/qna/reply")
@RequiredArgsConstructor
public class QnaReplyController {

    private final QnaReplyService qnaReplyService;

    // session 은 나중에 추가 해줄 예정
    // 게시판 댓글 작성
    @PostMapping("/{qnaNo}")
    public ResponseEntity<?> createReply(
            @Validated @RequestBody QnaReplyRequestDTO requestDTO,
            BindingResult result
    ){

        if (result.hasErrors()) {
            // ResponseEntity는 응답에 관련된 여러가지 정보 (상태코드, 전달할 데이터 등...) 를
            // 한번에 객체로 포장해서 리턴할 수 있게 하는 Spring에서 제공하는 객체.
            return ResponseEntity
                    .badRequest()
                    .body(result.toString());
        }

        QnaReplyListResponseDTO responseDTO = qnaReplyService.create(requestDTO);

        return ResponseEntity.ok().body(responseDTO);


    }

    // 댓글 상세보기
    @GetMapping("/{replyNo}")
    public ResponseEntity<?> boardReplyDetail(
            @PathVariable("replyNo") Long replyNo
    ){

        try {
            QnaReplyDetailResponseDTO responseDTO = qnaReplyService.qnaReplyDetail(replyNo);
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    // 페이지도 같이 넘겨줄 예정
    // 게시판 댓글 목록 조회
    @GetMapping("/list/{qnaNo}")
    public ResponseEntity<?> list(
            @PathVariable("qnaNo") Long qnaNo
    ){

        QnaReplyListResponseDTO responseDTO = qnaReplyService.getList(qnaNo);

        return ResponseEntity.ok().body(responseDTO);

    }


    // 게시판 댓글 삭제
    @DeleteMapping("/{replyNo}")
    public ResponseEntity<?> deleteReply(
            @PathVariable("replyNo") Long replyNo
    ){

        if(replyNo == null){
            return ResponseEntity
                    .badRequest()
                    .body("댓글 번호를 전달해 주세요.");
        }

        try {

            final QnaReplyListResponseDTO responseDTO = qnaReplyService.deleteReply(replyNo);
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){

            return ResponseEntity.internalServerError().body(e.getMessage());

        }


    }

    // 게시판 수정
//    @PatchMapping("/{replyNo}")
//    public ResponseEntity<?> updateReply(
//            @Validated @RequestBody QnaReplyUpdateRequestDTO requestDTO,
//            BindingResult result
//    ){
//
//        if(result.hasErrors()){
//            return ResponseEntity.badRequest()
//                    .body(result.toString());
//        }
//
//        try {
//            QnaReplyDetailResponseDTO responseDTO = qnaReplyService.update(requestDTO);
//            return ResponseEntity.ok().body(responseDTO);
//        }catch (Exception e){
//            return ResponseEntity.internalServerError().body(e.getMessage());
//        }
//
//
//
//
//
//    }
}
