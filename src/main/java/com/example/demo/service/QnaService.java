package com.example.demo.service;

import com.example.demo.dto.request.QnaAnswerRequestDTO;
import com.example.demo.dto.request.QnaUpdateRequestDTO;
import com.example.demo.entity.User;
import com.example.demo.dto.request.QnaRequestDTO;
import com.example.demo.dto.response.QnaDetailResponseDTO;
import com.example.demo.dto.response.QnaListResponseDTO;
import com.example.demo.entity.Qna;
import com.example.demo.repository.QnaRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QnaService {

    private final UserRepository userRepository;
    private final QnaRepository qnaRepository;

    public QnaListResponseDTO create(QnaRequestDTO requestDTO) {
      //  User user = getUser(userId);

        qnaRepository.save(requestDTO.toEntity());
        log.info("qna 작성 완료! qna 제목 : {}", requestDTO.getQTitle());
        log.info("qna 작성 완료! qna 내용 : {}", requestDTO.getQContent());

        return retrieve();
    }

    // qna detail (하나의 QnA 보여주는 로직)
    public QnaDetailResponseDTO qnaDetail(Long qnaNo) {

        Qna qna = qnaRepository.findById(qnaNo).orElseThrow(
                () -> new RuntimeException("존재하지 않은 QnA 입니다.")
        );

        return new QnaDetailResponseDTO(qna);


    }

    // qna 전체 목록 가져오기
    public QnaListResponseDTO retrieve(){
        List<Qna> entityList  = qnaRepository.findAll();

        List<QnaDetailResponseDTO> dtoList = entityList.stream()
                .map(QnaDetailResponseDTO::new)
                .toList();

        return QnaListResponseDTO.builder()
                .qnas(dtoList)
                .build();
    }

//    // qna 검색 시 목록 불러오기
//    public QnaListResponseDTO searchQna(String qTitle){
//        qnaRepository.QnaByTitle(qTitle);
//    }

    private User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("회원 정보가 없습니다.")
        );
        return user;
    }

    // qna 삭제
    public QnaListResponseDTO delete(final Long qnaNo) {

        qnaRepository.findById(qnaNo).orElseThrow(
                () -> {
                    log.error("글번호가 존재하지 않아 삭제에 실패하였습니다. qnaNo : {}", qnaNo);
                    throw new RuntimeException("글번호가 존재하지 않아 삭제에 실패하였습니다.");

                }
        );
        qnaRepository.deleteById(qnaNo);

        return retrieve();


    }

    // qna 수정
    public QnaListResponseDTO update(final QnaUpdateRequestDTO requestDTO) {

         Optional<Qna> byId = qnaRepository.findById(requestDTO.getQnaNo());

         byId.ifPresent(qna -> {
             qna.setQTitle(requestDTO.getQTitle());
             qna.setQContent(requestDTO.getQContent());
             qnaRepository.save(qna);
         });

         return retrieve();

    }


    public QnaDetailResponseDTO addAnswer(final QnaAnswerRequestDTO requestDTO) {

        Optional<Qna> addAnswer = qnaRepository.findById(requestDTO.getQnaNo());

        addAnswer.ifPresent(qna -> {
            qna.setQAnswer(requestDTO.getQAnswer());
            qnaRepository.save(qna);
        });

        return qnaDetail(requestDTO.getQnaNo());
    }

}
