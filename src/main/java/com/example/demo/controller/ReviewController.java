package com.example.demo.api;

import com.example.demo.dto.request.ReviewModifyRequestDTO;
import com.example.demo.dto.request.ReviewRequestDTO;
import com.example.demo.dto.response.ReviewListResponseDTO;
import com.example.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping("/create")
    public ResponseEntity<?> createReview(ReviewRequestDTO requestDTO) {
        reviewService.create(requestDTO);
        return ResponseEntity.ok().body("리뷰 작성이 완료되었습니다.");
    }

    // 리뷰 수정
    @PatchMapping
    public ResponseEntity<?> modifyReview(@RequestBody ReviewModifyRequestDTO requestDTO) {
        reviewService.modifyReview(requestDTO);
        return ResponseEntity.ok().body("리뷰 수정이 완료되었습니다.");
    }


    // 리뷰 삭제
    @DeleteMapping("/{reviewNo}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewNo) {
        reviewService.deleteReview(reviewNo);
        return ResponseEntity.ok().body("리뷰 삭제가 완료되었습니다");
    }


    // 리뷰 리스트 불러오기
    @GetMapping("/retrieve")
    public ResponseEntity<?> retrieveReview(@RequestParam(defaultValue = "") String userId, @RequestParam(defaultValue = "") String statId) {
        ReviewListResponseDTO responseDTO = new ReviewListResponseDTO();
        if (userId != null && !userId.isEmpty()) {
            responseDTO = reviewService.getList("userId", userId);
        } else if (statId != null && !statId.isEmpty()) {
            responseDTO = reviewService.getList("statId", statId);
        }

        return ResponseEntity.ok().body(responseDTO);
    }
}
