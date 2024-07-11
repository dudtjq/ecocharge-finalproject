package com.example.demo.controller;

import com.example.demo.auth.TokenUserInfo;
import com.example.demo.dto.request.QnaRequestDTO;
import com.example.demo.dto.request.QnaUpdateRequestDTO;
import com.example.demo.dto.response.QnaDetailResponseDTO;
import com.example.demo.dto.response.QnaListResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;

    @PostMapping
    public ResponseEntity<?> createQna(
            @Validated @RequestBody QnaRequestDTO requestDTO,
            BindingResult result){

        log.info("/ecocharge/qna Post! - dto: {}", requestDTO);
        log.info("TokenUserInfo: {}", requestDTO.getUserId());

        ResponseEntity<List<FieldError>> validatedResult = getValidatedResult(result);
        if(validatedResult != null) return validatedResult;

        QnaListResponseDTO qnaListResponseDTO = qnaService.create(requestDTO);
        log.info("Created Qna: {}", qnaListResponseDTO);
        return ResponseEntity.ok().body("작성 완료");

    }

    // QnA 상세보기
    @GetMapping("/{qnaNo}")
    public ResponseEntity<?> qnaDetail(
            @PathVariable("qnaNo") Long qnaNo
    ){
        log.info("/qna/detail GET response");


        try {
             QnaDetailResponseDTO responseDTO = qnaService.qnaDetail(qnaNo);
             return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    // qna 목록 리스트 요청
    @GetMapping
    public ResponseEntity<?> retrieveQnaList(@RequestParam(name = "page", defaultValue = "1") int pageNo){

        log.info("/qna GET request:{}",pageNo);
        try {

            log.info("All Find retrieved QnaList");
            QnaListResponseDTO responseDTO = qnaService.retrieve(pageNo);
            return ResponseEntity.ok().body(responseDTO);

        }catch (Exception e){

            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());

        }

    }

    @GetMapping("/user")
    public ResponseEntity<?> myRetrieveQnaList(@RequestParam(name = "page", defaultValue = "1") int pageNo
            ,   @RequestParam(value = "userId" ,required = false) String userId){

        log.info("/user/qna GET request:{}",pageNo);
        log.info("/user/qna GET request:{}",userId);

        try {


                QnaListResponseDTO responseDTO = qnaService.myRetrieve(pageNo,userId);
                log.info(" NOT ADMINretrieved MyQnaList: {}", responseDTO);
                return ResponseEntity.ok().body(responseDTO);

        }catch (Exception e){

            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());

        }

    }



    @GetMapping("/admin")
    public ResponseEntity<?> daminRetrieveQnaList(@RequestParam(name = "page", defaultValue = "1") int pageNo
            ,   @RequestParam(value = "userId" ,required = false) String userId
            ,    @RequestParam(value = "userRole" ,required = false) String userRole){

        log.info("/qna GET request:{}",pageNo);
        log.info("/qna GET request:{}",userId);
        log.info("/qna GET request:{}",userRole);

        try {

            if(userId !=null && !userId.isEmpty()&& !userRole.equals(("ADMIN"))){
                QnaListResponseDTO responseDTO = qnaService.myRetrieve(pageNo,userId);
                log.info(" NOT ADMINretrieved MyQnaList: {}", responseDTO);
                return ResponseEntity.ok().body(responseDTO);
            }

            if(userRole.equals(("ADMIN"))){
                QnaListResponseDTO responseDTO = qnaService.adminRetrieve(pageNo,userId, userRole);
                log.info("ADMIN retrieved MyQnaList: {}", responseDTO);
                return ResponseEntity.ok().body(responseDTO);
            }

            log.info("All Find retrieved QnaList");
            QnaListResponseDTO responseDTO = qnaService.retrieve(pageNo);
            return ResponseEntity.ok().body(responseDTO);

        }catch (Exception e){

            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());

        }

    }



    // QnA 삭제 요청 처리 (관리자)
    // 로그인 연동이 확인이 되면 qnaNo 와 함께 userInfo 넘겨줄 예정
    @DeleteMapping("/{qnaNo}")
    public ResponseEntity<?> deleteQna(
            @RequestParam("userId") String userId,
            @PathVariable("qnaNo") Long qnaNo
    ){

        log.info("DELETE request!: {}", qnaNo);
        log.info("userInfo : {}", userId);

        if(qnaNo == null){
            log.info("게시글이 없습니다.");
            return ResponseEntity.badRequest()
                    .body("QnA 번호를 전달해 주세요.");
        }

        try {

            QnaListResponseDTO responseDTO = qnaService.delete(qnaNo, userId);

            log.info("요청을 보냅니다:{}", responseDTO);

            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            e.printStackTrace();
            log.info("그 외의 오류");
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    // QnA 수정 요청 (관리자)
    // 로그인 연동이 확인이 되면 qnaNo 와 함께 userInfo 넘겨줄 예정
    @PatchMapping("/{qnaNo}")
    public ResponseEntity<?> updateQna(
            @Validated @RequestBody QnaUpdateRequestDTO requestDTO,
            @AuthenticationPrincipal TokenUserInfo userInfo,
            BindingResult result
            ){

        ResponseEntity<List<FieldError>> validatedResult = getValidatedResult(result);
        if(validatedResult != null) return validatedResult;

        try {

            QnaDetailResponseDTO updateDTO = qnaService.update(requestDTO, userInfo.getUserId());
            return ResponseEntity.ok().body(updateDTO);

        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }


    }

   //  QnA 답변 controller
    // 로그인 연동이 확인이 되면 qnaNo 와 함께 userInfo 넘겨줄 예정
   @PatchMapping("/add/{qnaNo}")
   public ResponseEntity<?> addAnswerToQna(@Validated @RequestBody QnaUpdateRequestDTO responseDTO) {
       try {
           log.info("responseDTO: {}", responseDTO.getQAnswer());
           log.info("no:{}",responseDTO.getQnaNo());
           // qnaNo와 responseDTO를 사용하여 답변 추가 처리 로직 수행
           QnaDetailResponseDTO updateAnswerDTO = qnaService.addAnswer( responseDTO);
           log.info("updateAnswerDTO: {}", updateAnswerDTO);
           return ResponseEntity.ok().body(updateAnswerDTO);
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
