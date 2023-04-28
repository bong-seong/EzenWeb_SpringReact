package ezenweb.web.controller;


import ezenweb.web.domain.board.BoardDto;
import ezenweb.web.domain.board.CategoryDto;
import ezenweb.web.domain.board.PageDto;
import ezenweb.web.domain.board.ReplyDto;
import ezenweb.web.service.BoardService;
import ezenweb.web.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// @CrossOrigin( origins = "http://localhost:3000")

@Slf4j
@RestController
@RequestMapping("/board")
public class BoardController {

    // 서비스 객체들
    @Autowired
    private BoardService boardService;

    @Autowired
    ReplyService replyService;
    /*
    // -------------------------------- view 반환 ---------------------------------- //
    @GetMapping("")
    public Resource index(){
        return new ClassPathResource("templates/board/list.html");
    }
    */

    // -------------------------------- model 반환 ---------------------------------- //
    // 1. 카테고리 등록
    @PostMapping("/category/write")
    public boolean categoryWrite(@RequestBody BoardDto boardDto ) {
        log.info("board dto : " + boardDto);
        return boardService.categoryWrite( boardDto );
    }

    // 4. 카테고리 출력 [ 반환타입 : { cno : cname , 1 : 공지사항 , 2 : 자유게시판 }
        // List : { 값 , 값 , 값 , 값 }
        // Map : { 키 : 값 , 키 : 값 , 키 : 값 } ---> JSON [ object ]
    @GetMapping("/category/list")
    public List<CategoryDto> categoryList() {
        return boardService.categoryList();
    }

    // 2. 게시물 쓰기 // 요청받은 JSON 필드명과 DTO 필드명 일치해야함
    @PostMapping("")
    public byte boardWrite (@RequestBody BoardDto boardDto ) {
        log.info("board dto : " + boardDto);
        return boardService.boardWrite( boardDto );
    }

    // 5. 전체 게시물 출력하기
    @GetMapping("")
    public PageDto boardList( PageDto dto ) {
        log.info("cno : " + dto.getCno() ); log.info("page : " + dto.getPage() );
        log.info("key : " + dto.getKey() ); log.info("keyword : " + dto.getKeyword() );
        return boardService.boardList( dto );
    }

    @PutMapping("")
    public boolean boardUpdate( @RequestBody BoardDto boardDto){
        return boardService.boardUpdate( boardDto );
    }

    // 7. 선택한 게시물 삭제
    @DeleteMapping("")
    public boolean deleteBoard( @RequestParam int bno ){
        return boardService.deleteBoard( bno );
    }

    // 3. 내가 쓴 게시물 출력하기
    @GetMapping("/myboards")
    public List<BoardDto> myboards() {
        log.info("myboard : ");
        return boardService.myboards();
    }

    // 6. 선택한 게시물 출력하기
    @GetMapping("/getboard")
    public BoardDto selectBoard( @RequestParam int bno ){
        return boardService.selectBoard( bno );
    }


    // *******************************************************

    @PostMapping("/reply")
    public boolean addReply(@RequestBody ReplyDto dto ){
        // return replyService.addReply(dto);
        log.info("ReplyDto : " + dto );
        return boardService.postReply(dto);
    }

    @GetMapping("/reply")
    public List<ReplyDto> getReply( @RequestParam int bno ){
        return replyService.getReply(bno);
    }

    @PutMapping("/reply")
    public boolean updateReply( @RequestBody ReplyDto replyDto ) {
        return boardService.updateReply(replyDto);
    }

    @DeleteMapping("/reply")
    public boolean deleteReply( @RequestParam int rno ){
        return boardService.deleteReply( rno );
    }

}
