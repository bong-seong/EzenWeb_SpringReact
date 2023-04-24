package ezenweb.web.controller;


import ezenweb.web.domain.board.BoardDto;
import ezenweb.web.domain.board.CategoryDto;
import ezenweb.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/board")
@CrossOrigin( origins = "http://localhost:3000")
public class BoardController {

    // 서비스 객체들
    @Autowired
    private BoardService boardService;
    // -------------------------------- view 반환 ---------------------------------- //
    @GetMapping("")
    public Resource index(){
        return new ClassPathResource("templates/board/list.html");
    }


    // -------------------------------- model 반환 ---------------------------------- //
    // 1. 카테고리 등록
    @PostMapping("/category/write")
    public boolean categoryWrite(@RequestBody BoardDto boardDto ) {
        log.info("board dto : " + boardDto);
        return boardService.categoryWrite( boardDto );
    }

    // 4. 카테고리 출력 [ 반환타입 : { cno : cname , 1 : 공지사항 , 2 : 자유게시판 }
        // List : { 값 ,값 , 값 , 값 }
        // Map : { 키 : 값 , 키 : 값 , 키 : 값 } ---> JSON [ object ]
    @GetMapping("/category/list")
    public List<CategoryDto> categoryList() {
        return boardService.categoryList();
    }

    // 2. 게시물 쓰기 // 요청받은 JSON 필드명과 DTO 필드명 일치해야함
    @PostMapping("/write")
    public byte boardWrite (@RequestBody BoardDto boardDto ) {
        log.info("board dto : " + boardDto);
        return boardService.boardWrite( boardDto );
    }

    // 5. 전체 게시물 출력하기
    @GetMapping("/list")
    public List<BoardDto> boardList( @RequestParam int cno ) {
        log.info("cno : " + cno);
        return boardService.boardList( cno );
    }

    // 3. 내가 쓴 게시물 출력하기
    @GetMapping("/myboards")
    public List<BoardDto> myboards() {
        log.info("myboard : ");
        return boardService.myboards();
    }

    // 6. 선택한 게시물 출력하기
    @GetMapping("/select")
    public BoardDto selectBoard( @RequestParam int bno ){
        return boardService.selectBoard( bno );
    }

    // 7. 선택한 게시물 삭제
    @DeleteMapping("/delete")
    public boolean deleteBoard( @RequestParam int bno ){
        return boardService.deleteBoard( bno );
    }


}
