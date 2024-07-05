package com.example.demo.service;

import com.example.demo.dto.request.ReviewModifyRequestDTO;
import com.example.demo.dto.request.ReviewRequestDTO;
import com.example.demo.dto.response.ReviewDetailResponseDTO;
import com.example.demo.dto.response.ReviewListResponseDTO;

import com.example.demo.entity.ChargeSpot;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;

import com.example.demo.repository.ChargeSpotRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ChargeSpotRepository chargeSpotRepository;

    public void create(final ReviewRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId()).orElseThrow();
        ChargeSpot chargeSpot = chargeSpotRepository.findById(requestDTO.getStatId()).orElseThrow();
        reviewRepository.save(requestDTO.toEntity(user, chargeSpot));
    }

    public ReviewListResponseDTO getList(String key, String reviewId) {

        List<Review> reviewList = new ArrayList<>();
        int count = 0;

        if (key.equals("userId")) {
            reviewList.addAll(reviewRepository.findAllByUserId(reviewId));
            count = reviewRepository.countByUserId(reviewId);
        } else if (key.equals("statId")) {
            reviewList.addAll(reviewRepository.findAllByUserId(reviewId));
            count = reviewRepository.countByUserId(reviewId);
        }

        List<ReviewDetailResponseDTO> dtoList = reviewList.stream()
                .map(ReviewDetailResponseDTO::new)
                .toList();

        return new ReviewListResponseDTO(count, dtoList);
    }

    public void modifyReview(ReviewModifyRequestDTO requestDTO) {
        Optional<Review> findReview = reviewRepository.findById(requestDTO.getReviewNo());
        findReview.ifPresent(review -> {
            review.setContent(requestDTO.getContent());
            review.setUpdateReviewDate(LocalDateTime.now());
            reviewRepository.save(review);
        });
    }

    public void deleteReview(Long reviewNo) {
        Review findReview = reviewRepository.findById(reviewNo).orElseThrow(() -> {
            log.error("리뷰가 존재하지 않습니다.");
            throw new RuntimeException("리뷰가 존재하지 않습니다.");
        });
        reviewRepository.deleteById(reviewNo);
    }
}
