package com.example.demo.qnaapi.controller;

import com.example.demo.auth.TokenUserInfo;
import com.example.demo.qnaapi.dto.request.QnaRequestDTO;
import com.example.demo.qnaapi.dto.response.QnaListResponseDTO;
import com.example.demo.qnaapi.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

        final ResponseEntity<List<FieldError>> validatedResult = getValidatedResult(result);
        if(validatedResult != null) return validatedResult;

        QnaListResponseDTO qnaListResponseDTO = qnaService.create(requestDTO);
        return ResponseEntity.ok().body(qnaListResponseDTO);

    }

    // qna 목록 요청
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
