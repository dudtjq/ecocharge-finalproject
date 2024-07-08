package com.example.demo.service;

import com.example.demo.auth.TokenUserInfo;
import com.example.demo.common.ItemWithSequence;
import com.example.demo.common.Page;
import com.example.demo.common.PageMaker;
import com.example.demo.dto.request.BoardRequestDTO;
import com.example.demo.dto.request.BoardUpdateRequestDTO;
import com.example.demo.dto.response.BoardDetailResponseDTO;
import com.example.demo.dto.response.BoardListResponseDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Qna;
import com.example.demo.entity.User;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.BoardRepositoryImpl;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;
    
    private final BoardRepositoryImpl boardRepositoryImpl;
    
    private final S3Service s3Service;

    public BoardListResponseDTO create(final BoardRequestDTO requestDTO, TokenUserInfo userInfo) throws IOException {

        User user = getUser(userInfo.getUserId());

        final String s = s3Service.uploadToS3Bucket(requestDTO.getBProfileImage().getBytes(), UUID.randomUUID().toString() + ".png");
        boardRepository.save(requestDTO.toEntity(s, user));

        return retrieve(1);

    }

    // board 전체 목록 가져오기
    public BoardListResponseDTO retrieve(int pageNo) {
        Page page = new Page();
        page.setPageNo(pageNo);
        List<ItemWithSequence> items = boardRepositoryImpl.findAll(page);
        
        List<BoardDetailResponseDTO> dtoList = items.stream()
                .map(item -> new BoardDetailResponseDTO(item.getBoard(), item.getSequence()))
                .toList();
        
        
        PageMaker pageMaker = new PageMaker(page, (int) boardRepository.count());
        
        return BoardListResponseDTO.builder()
                .boards(dtoList)
                .pageMaker(pageMaker)
                .build();
    }

    // 하나의 게시물 상세 보기
    public BoardDetailResponseDTO boardDetail(final Long boardNo) {

        Board board = boardRepository.findById(boardNo).orElseThrow(
                () -> new RuntimeException("존재하지 않는 게시물입니다.")
        );

        return new BoardDetailResponseDTO(board, 1);

    }


    // 게시물 삭제
    public BoardListResponseDTO delete(final Long boardNo, String userId) {

        final Board board = boardRepository.findById(boardNo).orElseThrow(
                () -> {
                    log.error("게시판 번호가 존재하지 않아 삭제에 실패하였습니다. qnaNo : {}", boardNo);
                    throw new RuntimeException("게시판 번호가 존재하지 않아 삭제에 실패하였습니다.");

                }
        );

        // 삭제 권한 체크 - 본인이거나 관리자만 삭제 가능
        if (!board.getUser().getUserId().equals(userId) && !userId.equals("ADMIN")) {
            log.error("해당 글을 삭제할 권한이 없습니다. boardNo: {}, userId: {}", boardNo, userId);
            throw new RuntimeException("글을 삭제할 권한이 없습니다.");
        }
        boardRepository.deleteById(boardNo);
        
        return retrieve(1);

    }


    public BoardDetailResponseDTO update(final BoardUpdateRequestDTO requestDTO, TokenUserInfo userInfo) {

        final Board board = boardRepository.findById(requestDTO.getBoardNo()).orElseThrow(
                () -> {
                    log.error("글번호가 존재하지 않아 수정에 실패하였습니다. qnaNo : {}", requestDTO.getBoardNo());
                    throw new RuntimeException("글번호가 존재하지 않아 수정에 실패하였습니다.");

                }
        );

        if (!board.getUser().getUserId().equals(userInfo.getUserId())) {
            log.error("해당 글을 수정할 권한이 없습니다. qnaNo: {}, userId: {}", requestDTO.getBoardNo(), userInfo.getUserId());
            throw new RuntimeException("글을 수정할 권한이 없습니다.");
        }

        Optional<Board> byId = boardRepository.findById(requestDTO.getBoardNo());

        // 본인만 수정 가능한 조건 체크


        byId.ifPresent(qna -> {
            qna.setBWriter(requestDTO.getBWriter());
            qna.setBTitle(requestDTO.getBTitle());
            qna.setBContent(requestDTO.getBContent());
            qna.setBAddress(requestDTO.getBAddress());
            qna.setBProfileImage(requestDTO.getBProfileImage());

            boardRepository.save(qna);
        });

        return boardDetail(requestDTO.getBoardNo());

    }

    private User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("회원 정보가 없습니다.")
        );
        return user;
    }



}
