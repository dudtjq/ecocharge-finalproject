package com.example.demo.api;

import com.example.demo.dto.request.BoardReplyRequestDTO;
import com.example.demo.dto.request.BoardReplyUpdateRequestDTO;
import com.example.demo.dto.response.BoardDetailResponseDTO;
import com.example.demo.dto.response.BoardReplyDetailResponseDTO;
import com.example.demo.dto.response.BoardReplyListResponseDTO;
import com.example.demo.entity.Board;
import com.example.demo.service.BoardReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ecocharge/board/reply")
@Slf4j
@RequiredArgsConstructor
public class BoardReplyController {

    private final BoardReplyService replyService;

    // session 은 나중에 추가 해줄 예정
    // 게시판 댓글 작성
    @PostMapping("/{boardNo}")
    public ResponseEntity<?> createReply(
            @Validated @RequestBody BoardReplyRequestDTO requestDTO,
            BindingResult result
    ){

        if (result.hasErrors()) {
            // ResponseEntity는 응답에 관련된 여러가지 정보 (상태코드, 전달할 데이터 등...) 를
            // 한번에 객체로 포장해서 리턴할 수 있게 하는 Spring에서 제공하는 객체.
            return ResponseEntity
                    .badRequest()
                    .body(result.toString());
        }

        BoardReplyListResponseDTO responseDTO = replyService.create(requestDTO);

        return ResponseEntity.ok().body(responseDTO);


    }

    // 댓글 상세보기
    @GetMapping("/{replyNo}")
    public ResponseEntity<?> boardReplyDetail(
            @PathVariable("replyNo") Long replyNo
    ){

        try {
            final BoardReplyDetailResponseDTO responseDTO = replyService.boardReplyDetail(replyNo);
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    // 페이지도 같이 넘겨줄 예정
    // 게시판 댓글 목록 조회
    @GetMapping("/list/{boardNo}")
    public ResponseEntity<?> list(
            @PathVariable("boardNo") Long boardNo
    ){

        final BoardReplyListResponseDTO responseDTO = replyService.getList(boardNo);

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

            BoardReplyListResponseDTO responseDTO = replyService.deleteReply(replyNo);
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){

            return ResponseEntity.internalServerError().body(e.getMessage());

        }


    }

    // 게시판 수정
    @PatchMapping("/{replyNo}")
    public ResponseEntity<?> updateReply(
            @Validated @RequestBody BoardReplyUpdateRequestDTO requestDTO,
            BindingResult result
            ){

        if(result.hasErrors()){
            return ResponseEntity.badRequest()
                    .body(result.toString());
        }

        try {
            BoardReplyDetailResponseDTO responseDTO = replyService.update(requestDTO);
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }





    }



}
