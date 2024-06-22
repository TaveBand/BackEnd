package ys_band.develop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ys_band.develop.domain.Board;
import ys_band.develop.repository.BoardRepository;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Board getBoard(Long boardId){
        return boardRepository.findById(boardId).orElseThrow(()->new RuntimeException("게시판을 찾을 수 없습니다."));


    }
    public Board getBoardByName(String name){
        return boardRepository.findByName(name)
                .orElseThrow(()-> new RuntimeException("게시판을 찾을 수 없습니다."));
    }
}
