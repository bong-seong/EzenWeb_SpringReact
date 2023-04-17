package ezenweb.web.controller;


import ezenweb.web.domain.board.BoardDto;
import ezenweb.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board")
public class BoardController {


    @Autowired
    private BoardService boardService;

    // 1. 카테고리 등록
    @PostMapping("/category/write")
    public boolean categoryWrite(@RequestBody BoardDto boardDto ) {
        log.info("board dto : " + boardDto);
        return boardService.categoryWrite( boardDto );
    }

    // 2. 게시물 쓰기
    @PostMapping("/write")
    public boolean boardWrite (@RequestBody BoardDto boardDto ) {
        log.info("board dto : " + boardDto);
        return boardService.boardWrite( boardDto );
    }

    // 3. 내가 쓴 게시물 출력하기
    @GetMapping("/myboards")
    public List<BoardDto> myboards() {
        log.info("myboard : ");
        return boardService.myboards();
    }

}
