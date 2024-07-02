package com.example.demo.service;

import com.example.demo.common.ItemWithSequence;
import com.example.demo.common.Page;
import com.example.demo.common.PageMaker;
import com.example.demo.dto.request.BoardRequestDTO;
import com.example.demo.dto.request.BoardUpdateRequestDTO;
import com.example.demo.dto.response.BoardDetailResponseDTO;
import com.example.demo.dto.response.BoardListResponseDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.BoardRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {


    private final BoardRepository boardRepository;
    
    private final BoardRepositoryImpl boardRepositoryImpl;
    
    private final S3Service s3Service;

    public BoardListResponseDTO create(final BoardRequestDTO requestDTO) throws IOException {


        final String s = s3Service.uploadToS3Bucket(requestDTO.getBProfileImage().getBytes(), UUID.randomUUID().toString() + ".png");
        boardRepository.save(requestDTO.toEntity(s));

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
    public BoardListResponseDTO delete(final Long boardNo) {

        boardRepository.findById(boardNo).orElseThrow(
                () -> {
                    log.error("게시판 번호가 존재하지 않아 삭제에 실패하였습니다. qnaNo : {}", boardNo);
                    throw new RuntimeException("게시판 번호가 존재하지 않아 삭제에 실패하였습니다.");

                }
        );
        boardRepository.deleteById(boardNo);
        
        return retrieve(1);

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
