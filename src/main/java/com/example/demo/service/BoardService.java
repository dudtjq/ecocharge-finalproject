package com.example.demo.service;

import com.example.demo.dto.request.BoardRequestDTO;
import com.example.demo.dto.request.BoardUpdateRequestDTO;
import com.example.demo.dto.request.QnaUpdateRequestDTO;
import com.example.demo.dto.response.BoardDetailResponseDTO;
import com.example.demo.dto.response.BoardListResponseDTO;
import com.example.demo.dto.response.QnaDetailResponseDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Qna;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {


    private final BoardRepository boardRepository;


    public BoardListResponseDTO create(final BoardRequestDTO requestDTO) {

        boardRepository.save(requestDTO.toEntity());

        return retrieve();

    }

    // board 전체 목록 가져오기
    public BoardListResponseDTO retrieve() {
        List<Board> boardList = boardRepository.findAll();

        List<BoardDetailResponseDTO> dtoList = boardList.stream()
                .map(BoardDetailResponseDTO::new)
                .toList();

        return BoardListResponseDTO.builder()
                .boards(dtoList)
                .build();
    }

    // 하나의 게시물 상세 보기
    public BoardDetailResponseDTO boardDetail(final Long boardNo) {

        Board board = boardRepository.findById(boardNo).orElseThrow(
                () -> new RuntimeException("존재하지 않는 게시물입니다.")
        );

        return new BoardDetailResponseDTO(board);

    }


    // 게시물 삭제
    public BoardListResponseDTO delete(final Long boardNo) {

        boardRepository.findById(boardNo).orElseThrow(
                () -> {
                    log.error("게시판 번호가 존재하지 않아 삭제에 실패하였습니다. qnaNo : {}", boardNo);
                    throw new RuntimeException("게시판 번호가 존재하지 않아 삭제에 실패하였습니다.");

                }
        );
        boardRepository.deleteById(boardNo);

        return retrieve();

    }


    public BoardDetailResponseDTO update(final BoardUpdateRequestDTO requestDTO) {

        Optional<Board> byId = boardRepository.findById(requestDTO.getBoardNo());

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



}
