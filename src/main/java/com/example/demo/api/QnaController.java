package com.example.demo.api;

import com.example.demo.auth.TokenUserInfo;
import com.example.demo.dto.request.QnaAnswerRequestDTO;
import com.example.demo.dto.request.QnaRequestDTO;
import com.example.demo.dto.request.QnaUpdateRequestDTO;
import com.example.demo.dto.response.QnaDetailResponseDTO;
import com.example.demo.dto.response.QnaListResponseDTO;
import com.example.demo.service.QnaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ecocharge/qna")
public class QnaController {

    private final QnaService qnaService;

    @PostMapping
    public ResponseEntity<?> createQna(
            @AuthenticationPrincipal TokenUserInfo userInfo,
            @Validated @RequestBody QnaRequestDTO requestDTO,
            BindingResult result){

        log.info("/ecocharge/qna GET! - dto: {}", requestDTO);
        log.info("TokenUserInfo: {}", userInfo);

        ResponseEntity<List<FieldError>> validatedResult = getValidatedResult(result);
        if(validatedResult != null) return validatedResult;

        QnaListResponseDTO qnaListResponseDTO = qnaService.create(requestDTO);
        return ResponseEntity.ok().body(qnaListResponseDTO);

    }

    // QnA 상세보기
    @GetMapping("/{qnaNo}")
    public ResponseEntity<?> qnaDetail(
            @PathVariable("qnaNo") Long qnaNo
    ){
        log.info("/ecocharge/qna/detail GET response");


        try {
             QnaDetailResponseDTO responseDTO = qnaService.qnaDetail(qnaNo);
             return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    // qna 목록 리스트 요청
    @GetMapping
    public ResponseEntity<?> retrieveQnaList(){

        log.info("/ecocharge/qna GET request");

        try {

            QnaListResponseDTO responseDTO = qnaService.retrieve();
            return ResponseEntity.ok().body(responseDTO);

        }catch (Exception e){

            return ResponseEntity
                    .internalServerError()
                    .body(QnaListResponseDTO.builder()
                            .error(e.getMessage())
                            .build());

        }

    }

    // QnA 삭제 요청 처리 (관리자)
    // 로그인 연동이 확인이 되면 qnaNo 와 함께 userInfo 넘겨줄 예정
    @DeleteMapping("/{qnaNo}")
    public ResponseEntity<?> deleteQna(
            @AuthenticationPrincipal TokenUserInfo userInfo,
            @PathVariable("qnaNo") Long qnaNo
    ){

        log.info("/api/todos/{} DELETE request!", qnaNo);

        if(qnaNo == null){
            return ResponseEntity.badRequest()
                    .body("QnA 번호를 전달해 주세요.");
        }

        try {
            QnaListResponseDTO responseDTO = qnaService.delete(qnaNo);
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    // QnA 수정 요청 (관리자)
    // 로그인 연동이 확인이 되면 qnaNo 와 함께 userInfo 넘겨줄 예정
    @PatchMapping
    public ResponseEntity<?> updateQna(
            @Validated @RequestBody QnaUpdateRequestDTO requestDTO,
            BindingResult result
            ){

        ResponseEntity<List<FieldError>> validatedResult = getValidatedResult(result);
        if(validatedResult != null) return validatedResult;

        try {

            QnaListResponseDTO updateDTO = qnaService.update(requestDTO);
            return ResponseEntity.ok().body(updateDTO);

        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }


    }

    // QnA 답변 controller
    // 로그인 연동이 확인이 되면 qnaNo 와 함께 userInfo 넘겨줄 예정
    @PatchMapping("/add-answer/{qnaNo}")
    @Transactional
    public ResponseEntity<?> addAnswerToQna(
            @Validated @RequestBody QnaAnswerRequestDTO requestDTO,
            BindingResult result
            ) {

        ResponseEntity<List<FieldError>> validatedResult = getValidatedResult(result);
        if(validatedResult != null) return validatedResult;

        try {
             QnaDetailResponseDTO responseDTO = qnaService.addAnswer(requestDTO);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    // 입력값 검증(Validation)의 결과를 처리해 주는 전역 메서드
    private static ResponseEntity<List<FieldError>> getValidatedResult(BindingResult result) {
        if (result.hasErrors()) { // 입력값 검증 단계에서 문제가 있었다면 true
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(err -> {
                log.warn("invalid client data - {}", err.toString());
            });
            return ResponseEntity
                    .badRequest()
                    .body(fieldErrors);
        }
        return null;
    }



}
