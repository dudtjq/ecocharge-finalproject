package com.example.demo.service;

import com.example.demo.auth.TokenUserInfo;
import com.example.demo.common.ItemWithSequence;
import com.example.demo.common.Page;
import com.example.demo.common.PageMaker;
import com.example.demo.dto.request.QnaUpdateRequestDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.dto.request.QnaRequestDTO;
import com.example.demo.dto.response.QnaDetailResponseDTO;
import com.example.demo.dto.response.QnaListResponseDTO;
import com.example.demo.entity.Qna;
import com.example.demo.repository.QnaRepository;
import com.example.demo.repository.QnaRepositoryImpl;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
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
    private final QnaRepositoryImpl qnaRepositoryImpl;

    public QnaListResponseDTO create(QnaRequestDTO requestDTO) {
        log.info("create 작동");
        // userId를 사용하여 User 엔티티를 조회
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("회원정보를 찾을 수 없습니다."));

        // Qna 엔티티 생성
        Qna qna = requestDTO.toEntity(user);

        qnaRepository.save(qna);
        log.info("qna 작성 완료! qna : {}", requestDTO);

        return retrieve(1);
    }

    // qna detail (하나의 QnA 보여주는 로직)
    public QnaDetailResponseDTO qnaDetail(final Long qnaNo) {

        Qna qna = qnaRepository.findById(qnaNo).orElseThrow(
                () -> new RuntimeException("존재하지 않은 QnA 입니다.")
        );

        return new QnaDetailResponseDTO(qna, 1);

    }

    // qna 전체 목록 가져오기
    public QnaListResponseDTO retrieve(int pageNo) {
        log.info("Allpage no: {}", pageNo);
        Page page = new Page();
        page.setPageNo(pageNo);
        log.info("page: {}", page);

        
        List<ItemWithSequence> entityList = qnaRepositoryImpl.findAll(page);
        log.info("entityList : {}", entityList);


        List<QnaDetailResponseDTO> dtoList = entityList.stream()
                .map(item -> {
                        log.info(item.getQna().toString());
                        log.info(item.getQna().getUser().toString());
                        return new QnaDetailResponseDTO(item.getQna(), item.getSequence());
                }
                )
                .toList();


        log.info("dtoList : {}", dtoList);
        
        PageMaker pageMaker = new PageMaker(page, (int) qnaRepository.count());
        log.info("pageMaker : {}", pageMaker);

        return QnaListResponseDTO.builder()
                .qnas(dtoList)
                .pageMaker(pageMaker)
                .build();
    }

    // qna 개인 목록 가져오기
    public QnaListResponseDTO myRetrieve(int pageNo, String userId) {
        Page page = new Page();
        page.setPageNo(pageNo);

        log.info("userid:{}",userId);

        // userId를 사용하여 User 엔티티를 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("회원정보를 찾을 수 없습니다."));

        log.info("user:{}",user);

        // User 엔티티를 기준으로 페이지 조회
        List<ItemWithSequence> entityList = qnaRepositoryImpl.findAllByUser(page, user);
        log.info("entityList : {}", entityList);

        List<QnaDetailResponseDTO> dtoList = entityList.stream()
                .map(item -> new QnaDetailResponseDTO(item.getQna(), item.getSequence()))
                .toList();
        log.info("dtoList : {}", dtoList);

        PageMaker pageMaker = new PageMaker(page, entityList.size());

        return QnaListResponseDTO.builder()
                .qnas(dtoList)
                .pageMaker(pageMaker)
                .build();
    }

    public QnaListResponseDTO adminRetrieve(int pageNo, String userId, String userRole) {
        Page page = new Page();
        page.setPageNo(pageNo);

        log.info("userid:{}",userRole);

        // userId를 사용하여 User 엔티티를 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("회원정보를 찾을 수 없습니다."));

        log.info("user:{}",user.getRole());

        String role= String.valueOf(user.getRole());
        if(role=="ADMIN"){
            // User 엔티티를 기준으로 페이지 조회
            log.info("관리자 게시글 탐색로직 ");
            List<ItemWithSequence> entityList = qnaRepositoryImpl.findAllByAdminRole(page);
            log.info("entityList : {}", entityList);

            List<QnaDetailResponseDTO> dtoList = entityList.stream()
                    .map(item -> new QnaDetailResponseDTO(item.getQna(), item.getSequence()))
                    .toList();
            log.info("dtoList : {}", dtoList);

            PageMaker pageMaker = new PageMaker(page, (int) qnaRepository.count());

            return QnaListResponseDTO.builder()
                    .qnas(dtoList)
                    .pageMaker(pageMaker)
                    .build();
        }

        return null;
    }



    private User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("회원 정보가 없습니다.")
        );
        return user;
    }

    // qna 삭제
    @Transactional
    public QnaListResponseDTO delete(final Long qnaNo, String userId) {


       User user = userRepository.findById(userId).orElseThrow();
        Qna qna = qnaRepository.findById(qnaNo).orElseThrow();

        // 삭제 권한 체크 - 본인이거나 관리자만 삭제 가능
        if (!user.getRole().equals(Role.ADMIN) && !qna.getUser().getUserId().equals(userId)) {
            log.info(user.toString());
            log.error("해당 글을 삭제할 권한이 없습니다. qnaNo: {}, userId: {}", qnaNo, userId);
            throw new RuntimeException("글을 삭제할 권한이 없습니다.");
        }


        qnaRepository.deleteByQnaNo(qnaNo);

        return retrieve(1);


    }

    // qna 수정
    public QnaDetailResponseDTO update(final QnaUpdateRequestDTO requestDTO, String userId) {

        final Qna qna = qnaRepository.findById(requestDTO.getQnaNo()).orElseThrow(
                () -> {
                    log.error("글번호가 존재하지 않아 수정에 실패하였습니다. qnaNo : {}", requestDTO.getQnaNo());
                    throw new RuntimeException("글번호가 존재하지 않아 수정에 실패하였습니다.");

                }
        );

        // 본인만 수정 가능한 조건 체크
        if (!qna.getUser().getUserId().equals(userId)) {
            log.error("해당 글을 수정할 권한이 없습니다. qnaNo: {}, userId: {}", requestDTO.getQnaNo(), userId);
            throw new RuntimeException("글을 수정할 권한이 없습니다.");
        }

        Optional<Qna> byId = qnaRepository.findById(requestDTO.getQnaNo());

        byId.ifPresent(qna1 -> {
            qna1.setQTitle(requestDTO.getQTitle());
            qna1.setQContent(requestDTO.getQContent());
            qnaRepository.save(qna1);
        });

        return qnaDetail(requestDTO.getQnaNo());

    }


   //  답변 추가 로직
    public QnaDetailResponseDTO addAnswer(QnaUpdateRequestDTO requestDTO) {
        Optional<Qna> qnaByNo = qnaRepository.findById(requestDTO.getQnaNo());

        log.info("테이블번호:{}",qnaByNo);

        qnaByNo.ifPresent(qna -> {
            qna.setQAnswer(requestDTO.getQAnswer());
            log.info("getQAnswer: {}", requestDTO.getQAnswer());
            qnaRepository.save(qna);
        });
        return qnaDetail(requestDTO.getQnaNo());
    }

}